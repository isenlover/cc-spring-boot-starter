package pers.cc.spring.api.aliyun.sms.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.aliyun.common.annotation.AliyunParam;
import pers.cc.spring.api.aliyun.common.bean.AliyunParamBean;
import pers.cc.spring.api.aliyun.common.exception.AliyunRuntimeException;
import pers.cc.spring.api.aliyun.sms.annotation.AliyunSMSParam;
import pers.cc.spring.api.aliyun.sms.bean.AliyunSMSParamBean;
import pers.cc.spring.api.aliyun.sms.model.AliyunSMSCodeVO;
import pers.cc.spring.api.aliyun.sms.model.AliyunSMSDTO;
import pers.cc.spring.api.aliyun.sms.model.AliyunSMSResponseMessageVO;
import pers.cc.spring.api.aliyun.sms.service.AliyunSMSService;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.core.util.other.DateUtils;
import pers.cc.spring.core.util.other.MathUtils;
import pers.cc.spring.data.redis.service.RedisService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * 阿里云短信接口实现
 *
 * @author chengce
 * @version 2017-10-16 21:02
 */
@Slf4j
@Primary
@Service
@ConditionalOnBean(annotation = {AliyunSMSParam.class, AliyunParam.class})
public class AliyunSMSImpl implements AliyunSMSService {

  //产品名称:云通信短信API产品,开发者无需替换
  private final String product = "Dysmsapi";

  //产品域名,开发者无需替换
  private final String domain = "dysmsapi.aliyuncs.com";

  @Resource
  private AliyunParamBean aliyunParamBean;

  @Resource
  private AliyunSMSParamBean aliyunSMSParamBean;

  @Autowired
  private RedisService redisService;

  public <T> T sendSMS(AliyunSMSDTO aliyunSMS, Class<T> tClass) throws ClientException {
    //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(codeTimeout));
//        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(codeTimeout));
    //初始化acsClient,暂不支持region化
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunParamBean.getKey(),
        aliyunParamBean.getSecret());
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);

    //组装请求对象-具体描述见控制台-文档部分内容
    SendSmsRequest request = new SendSmsRequest();
    //必填:待发送手机号
    request.setPhoneNumbers(aliyunSMS.getMobile());
    //必填:短信签名-可在短信控制台中找到，签名名称
    request.setSignName(aliyunSMSParamBean.getSign());
    //必填:短信模板-可在短信控制台中找到，模板Code
    request.setTemplateCode(aliyunSMS.getTemplate());
//        String code = CCUtil.getRandom(4, CCUtil.NonceType.NonceTypeNumber);
    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\"number\":\"" + code + "\"}");
    request.setTemplateParam(aliyunSMS.getContent());

    //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
    //request.setSmsUpExtendCode("90997");

    //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    request.setOutId(aliyunSMS.getOutId());

    //hint 此处可能会抛出异常，注意catch
    AliyunSMSResponseMessageVO aliyunSMSResponseMessageVO = AliyunSMSResponseMessageVO.builder()
        .mobile(aliyunSMS.getMobile())
        .sendSmsResponse(acsClient.getAcsResponse(request))
        .timestamp(DateUtils.getTimestamp())
        .build();
    if (tClass != null) {
      return ClassUtils.transferClass(aliyunSMSResponseMessageVO, tClass);
    }
    return (T) aliyunSMSResponseMessageVO;
  }

  @Override
  public <T> T sendSMS(AliyunSMSDTO aliyunSMS) throws ClientException {
    return sendSMS(aliyunSMS, null);
  }

  @Override
  public Message<String> sendSMSCode(String mobile) throws ClientException {
    String cacheKey = getSMSCacheKey(mobile);
    AliyunSMSCodeVO aliyunSMSCodeVO = redisService.getValue(cacheKey);
    if (CommonUtils.isNotEmpty(
        aliyunSMSCodeVO) && DateUtils.getTimestamp() - aliyunSMSCodeVO.getTimestamp() < aliyunSMSParamBean.getTimeout() * 1000) {
      throw new AliyunRuntimeException();
    }
    String code = MathUtils.getRandom(4, MathUtils.NonceType.NUMBER);
    String content = "{\"" + aliyunSMSParamBean.getParam() + "\":\"" + code + "\"}";
    aliyunSMSCodeVO = sendSMS(
        AliyunSMSDTO.builder().mobile(mobile).template(aliyunSMSParamBean.getTemplate()).content(content).build(),
        AliyunSMSCodeVO.class
    );
    aliyunSMSCodeVO.setCode(code);
    if (aliyunSMSCodeVO.getSendSmsResponse().getCode() != null && aliyunSMSCodeVO.getSendSmsResponse().getMessage().equals("OK")) {
      redisService.cacheValue(cacheKey, aliyunSMSCodeVO, aliyunSMSParamBean.getExpiryTime());
    } else {
      throw new AliyunRuntimeException(aliyunSMSCodeVO.getSendSmsResponse().getMessage());
    }
    return Message.ok(code);
  }

  private String getSMSCacheKey(String mobile) {
    return "ALIYUN_SMS_WITH_MOBILE_" + mobile;
  }

  @Override
  public boolean removeCode(String mobile) {
    redisService.remove(getSMSCacheKey(mobile));
    return true;
  }

  @Override
  public boolean checkCode(String mobile, String code) {
    if (CommonUtils.isEmpty(mobile) || CommonUtils.isEmpty(code)) {
      return false;
    }
    AliyunSMSCodeVO aliyunSMSCodeVO = redisService.getValue(getSMSCacheKey(mobile));
    return aliyunSMSCodeVO != null && code.equals(aliyunSMSCodeVO.getCode());
  }

  @Override
  public boolean checkCode(String mobile, String code, boolean logoff) {
    boolean check = checkCode(mobile, code);
    if (check && logoff) {
      return removeCode(mobile);
    } else {
      return check;
    }
  }
}
