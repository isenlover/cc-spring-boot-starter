package pers.cc.spring.api.wechat.app.service.impl;


import io.lettuce.core.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.annotation.EnableWechatOpen;
import pers.cc.spring.api.wechat.app.model.dto.AccessTokenCode;
import pers.cc.spring.api.wechat.app.model.dto.WxUserInfoDTO;
import pers.cc.spring.api.wechat.app.service.WxAppUserService;
import pers.cc.spring.api.wechat.bean.WechatInformation;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.message.Message;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 微信用户接口实现
 *
 * @author chengce
 * @version 2017-07-10 下午8:32
 * @fixed 2021-03-18
 */
@Primary
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WxAppUserImpl implements WxAppUserService {

  @Value("${wechat.appUrl.accessToken}")
  private String url_accessToken;

  @Value("${wechat.appUrl.userInfo}")
  private String url_userInfo;

  @Value("${wechat.appUrl.refreshToken}")
  private String url_refreshToken;

  //    @Value("${wechat.appId}")
//    private String appId;
//
//    @Value("${wechat.appSecret}")
//    private String appSecret;
//
  @Autowired
  WechatInformation wxAppBean;

  private final String cachePre = "wxAppAccessToken_";

//    @Autowired
//    private RedisService redisService;

  @Override
  public Message<WxUserInfoDTO> getUserInfo(String accessToken, String openid) {
    return getUserInfo(accessToken, openid, "zh-CN");
  }

  // FIXME: 2018/6/1
  @Override
  public Message getUserInfo(String openId) {
//        AccessTokenCodeDTO accessTokenCodeDTO = redisService.getValue(cachePre + openId);
//        if (CommonUtils.isEmpty(accessTokenCodeDTO)) {
//            return Message.failed().message(CcBadRequestMessage.FORBIDDEN_WX_EXPIRED.getMessage()).build();
//        } else {
//            Message<AccessTokenCodeDTO> message = refreshToken(openId, accessTokenCodeDTO.getRefresh_token());
//            if (message.isSuccess()) {
//                return getUserInfo(message.getData().getAccess_token(), message.getData().getOpenid());
//            }
//        }
//        return Message.failed().message(CcBadRequestMessage.BAD_REQUEST_WX_USER_FAILED.getMessage()).build();
    return null;
  }

  @Override
  public Message<WxUserInfoDTO> getUserInfo(String accessToken, String openid, String lang) {
    String requestUrl = url_userInfo
        .replace("ACCESS_TOKEN", accessToken)
        .replace("zh_CN", lang)
        .replace("OPENID", openid);
    return WechatUtil.httpsGetWechat(requestUrl, WxUserInfoDTO.class);
  }

  @Override
  public Message<AccessTokenCode> getAccessToken(String code) throws RedisException {
    String requestUrl = url_accessToken
        .replace("APPID", wxAppBean.getAppId())
        .replace("SECRET", wxAppBean.getAppSecret())
        .replace("CODE", code);

    Message<AccessTokenCode> message = WechatUtil.httpsGetWechat(requestUrl, AccessTokenCode.class);
    if (message.isSuccess()) {
      final String key = cachePre + message.getData().getOpenid();
      // FIXME: 2018/6/1
//            redisService.cacheValue(key, message.getData(), 29, TimeUnit.DAYS);
    }
    return message;
  }

  @Override
  public Message<AccessTokenCode> refreshToken(String openId, String refreshToken) throws RedisException {
    String requestUrl = url_refreshToken
        .replace("APPID", wxAppBean.getAppId())
        .replace("REFRESH_TOKEN", refreshToken);
    return WechatUtil.httpsGetWechat(requestUrl, AccessTokenCode.class);
  }
}
