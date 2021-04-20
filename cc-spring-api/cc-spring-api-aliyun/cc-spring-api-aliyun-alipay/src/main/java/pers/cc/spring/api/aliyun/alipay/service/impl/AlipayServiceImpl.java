package pers.cc.spring.api.aliyun.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.aliyun.alipay.annotation.EnableAliPay;
import pers.cc.spring.api.aliyun.alipay.bean.AlipayParamBean;
import pers.cc.spring.api.aliyun.alipay.bean.PayModel;
import pers.cc.spring.api.aliyun.alipay.service.AlipayService;
import pers.cc.spring.core.exception.BaseRuntimeException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengce
 * @version 2021-04-20 13:07
 */
@Slf4j
@Service
@ConditionalOnBean(annotation = EnableAliPay.class)
public class AlipayServiceImpl implements AlipayService {

  @Autowired
  AlipayParamBean alipayParamBean;

  @Override
  public String pay(PayModel payModel) {
    try {
      /** 支付宝网关 **/
      String URL = "https://openapi.alipay.com/gateway.do";

      /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
      String APP_ID = alipayParamBean.getAppId();

      /** 应用私钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602469554 **/
      String APP_PRIVATE_KEY = alipayParamBean.getAppPrivateKey();

      /** 支付宝公钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602487431 **/
      // MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwxHG6FoF71tH1z8YvXUIxNnWUML49tLmr8331zBsDJmOhuvKjXzbS+1Q4ifhJMDVpovKY9Vbyl85pQVXRg1onwC8An8lyu9ENobiV+paFJ08CizEDxSLrcJxkJqhW7ono+IDv17hkE75FtuAAx5nimPJN6RgbC4Xa9AYNGPlqHqzBReBn2gt+IKJk5BW3XrK47llub/lqiHoA9t4F3kqnSjOLmG65Xigk7r8X37h9EjQHlNAlWCk+++f3uzkCAcDOmHzIT6t1xBInq8IjbOJcqDVMezZCsEzV3U7+R3rOqAwYNL7aeJyJk7rHbIrhLKeMU+kuQ41Elem0saNuaXVbQIDAQAB
      String ALIPAY_PUBLIC_KEY = alipayParamBean.getAlipayPublicKey();

      /** 初始化 **/
      AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");

      /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.page.pay（电脑网站支付） **/
      AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

      AlipayTradePagePayModel pagePayModel = payModel.getPagePayModel();
      pagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");
      /** 设置业务参数  **/
//      AlipayTradePagePayModel model = new AlipayTradePagePayModel();
//
//      /** 商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001 **/
//      model.setOutTradeNo("20200612000001");
//
//      /** 销售产品码，固定值：FAST_INSTANT_TRADE_PAY **/
//      model.setProductCode("FAST_INSTANT_TRADE_PAY");
//
//      /**订单标题 **/
//      model.setSubject("订单标题");
//
//      /** 订单金额，精确到小数点后两位 **/
//      model.setTotalAmount("0.1");
//
//      /** 订单描述 **/
//      model.setBody("订单描述");

      /** 业务扩展参数 **/
      //ExtendParams extendParams = new ExtendParams();

      /** 花呗分期参数传值前提：必须有该接口花呗收款准入条件，且需签约花呗分期 **/
      /** 指定可选期数，只支持3/6/12期，还款期数越长手续费越高 **/
      // extendParams.setHbFqNum("3");

      /** 指定花呗分期手续费承担方式，手续费可以由用户全承担（该值为0），也可以商户全承担（该值为100），但不可以共同承担，即不可取0和100外的其他值。 **/
      //extendParams.setHbFqSellerPercent("0");

      //model.setExtendParams(extendParams);

      /** 将业务参数传至request中 **/
      alipayRequest.setBizModel(pagePayModel);

      /** 注：支付结果以异步通知为准，不能以同步返回为准，因为如果实际支付成功，但因为外力因素，如断网、断电等导致页面没有跳转，则无法接收到同步通知；**/
      /** 同步通知地址，以http或者https开头，支付完成后跳转的地址，用于用户视觉感知支付已成功，传值外网可以访问的地址，如果同步未跳转可参考该文档进行确认：https://opensupport.alipay.com/support/helpcenter/193/201602474937 **/
      alipayRequest.setReturnUrl(payModel.getReturnUrl());

      /** 异步通知地址，以http或者https开头，商户外网可以post访问的异步地址，用于接收支付宝返回的支付结果，如果未收到该通知可参考该文档进行确认：https://opensupport.alipay.com/support/helpcenter/193/201602475759 **/
      alipayRequest.setNotifyUrl(payModel.getNotifyUrl());

      /** 第三方调用（服务商模式），传值app_auth_token后，会收款至授权app_auth_token对应商家账号，如何获传值app_auth_token请参考文档：https://opensupport.alipay.com/support/helpcenter/79/201602494631 **/
      //request.putOtherTextParam("app_auth_token", "传入获取到的app_auth_token值");

      String form;
      try {

        /** 调用SDK生成表单form表单 **/
        form = alipayClient.pageExecute(alipayRequest).getBody();

        /** 调用SDK生成支付链接，可在浏览器打开链接进入支付页面 **/
        //form = alipayClient.pageExecute(alipayRequest,"GET").getBody();
        return form;
      } catch (AlipayApiException e) {
        e.printStackTrace();
        throw new BaseRuntimeException("支付失败");
      }

      /** 获取接口调用结果，如果调用失败，可根据返回错误信息到该文档寻找排查方案：https://opensupport.alipay.com/support/helpcenter/97 **/
//      httpServletResponse.setContentType("text/html;charset=" + "utf-8");
//
//      /** 直接将完整的表单html输出到页面 **/
//      httpServletResponse.getWriter().write(form);
//      httpServletResponse.getWriter().flush();
//      // 1. 创建AlipayClient实例
//      AlipayClient alipayClient = new DefaultAlipayClient(getClientParams());
//      // 2. 创建使用的Open API对应的Request请求对象
//      AlipayOpenOperationOpenbizmockBizQueryRequest request = getRequest();
//      // 3. 发起请求并处理响应
//      AlipayOpenOperationOpenbizmockBizQueryResponse response = alipayClient.certificateExecute(request);
//      if (response.isSuccess()) {
//        System.out.println("调用成功。");
//      } else {
//        System.out.println("调用失败，原因：" + response.getMsg() + "，" + response.getSubMsg());
//      }
    } catch (Exception e) {
      System.out.println("调用遭遇异常，原因：" + e.getMessage());
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Override
  public boolean checkSignByKey(HttpServletRequest httpServletRequest) {
    try {
      log.info("checkSign4");
      Map<String, String> params = new HashMap<>();
      Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
      for (String name : requestParams.keySet()) {
        String[] values = requestParams.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
          valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
        }
        //乱码解决，这段代码在出现乱码时使用。
        //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
        params.put(name, valueStr);
      }
      return AlipaySignature.rsaCheckV1(params, alipayParamBean.getAlipayPublicKey(), "UTF-8", "RSA2");
    } catch (AlipayApiException e) {
      e.printStackTrace();
    }
    return false;
  }
}
