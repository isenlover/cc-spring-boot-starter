package pers.cc.spring.api.wechat.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;
import pers.cc.spring.api.wechat.model.pay.response.WxPayMessage;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.message.WechatMessageCode;
import pers.cc.spring.core.util.http.HttpUtils;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.core.util.xml.JdomXmlUtils;

import java.util.Map;

/**
 * 微信工具类
 *
 * @author chengce
 * @version 2016-07-25 15:09
 */
@Slf4j
public class WechatUtil {

//  /**
//   * 去掉Url中的ACCESS_TOKEN
//   *
//   * @param url url
//   * @return realBet url
//   */
//  public static String getRealUrlReplaceAccessToken(String url) {
//    return url.replace("ACCESS_TOKEN", AccessTokenBo.getInstance().getAccess_token());
//  }

  /**
   * 检查微信请求是否成功
   *
   * @param httpMessage Http Message类
   * @param baseObject  微信端返回的基本JSON类
   * @return 是否成功
   */
  public static boolean checkRequest(Message httpMessage, Object baseObject) {
    if (baseObject instanceof WxBaseResponse) {
      WxBaseResponse baseErr = (WxBaseResponse) baseObject;
      httpMessage.setMessage(baseErr.getErrmsg());
      httpMessage.setCode(String.valueOf(baseErr.getErrcode()));
      if (baseErr.getErrcode() == 0) {
        httpMessage.setSuccess(true);
        return true;
      }
    } else {
      httpMessage.setSuccess(true);
      return true;
    }
    return false;
  }

  /**
   * 检查微信请求是否成功(xml)
   *
   * @param httpMessage Http Message类
   * @param map         微信端返回的基本Xml类
   * @return 是否成功
   */
  public static boolean checkRequestXml(Message httpMessage, Map<String, Object> map) {
    if (map == null) {
      httpMessage.setMessage("空值");
      return false;
    }
    if (map.get("return_code").equals("SUCCESS")) {
      httpMessage.setSuccess(true);
      return true;
    } else {
      httpMessage.setMessage((String) map.get("return_msg"));
    }
    return false;
  }

  /**
   * 发起微信Https请求 适用于大部分Https请求
   *
   * @param requestUrl 请求地址
   * @param method     请求方式
   * @param params     请求参数
   * @param clazz      泛型类型
   * @return Message<T>
   */
  private static <T> Message<T> httpsRequestWechat(String requestUrl, String method, String params, Class<T> clazz) {
    Message<T> httpMessage = new Message<>();
    try {
      String response = HttpUtils.httpsRequest(requestUrl, method, params);
      log.info("wechat response：" + response);
      T data = JSON.parseObject(response, clazz);
      if (WechatUtil.checkRequest(httpMessage, data)) {
        httpMessage.setData(data);
      }
    } catch (Exception e) {
      e.printStackTrace();
      httpMessage.setCode(WechatMessageCode.BAD_REQUEST_WECHAT_HTTP_ANALYSIS.getCode());
      httpMessage.setMessage(WechatMessageCode.BAD_REQUEST_WECHAT_HTTP_ANALYSIS.getMessage());
      return httpMessage;
    }
    return httpMessage;
  }

  /**
   * 微信支付专用https 解析来自xml的回复
   *
   * @param requestUrl 请求地址
   * @param params     请求参数
   * @return Message<T>
   */
  public static Message<WxPayMessage> httpsPay(String requestUrl, String params) {
    Message<WxPayMessage> httpMessage = new Message<>();
    try {
      String response = HttpUtils.httpsRequest(requestUrl, "POST", params);
      Map<String, Object> data = JdomXmlUtils.doXMLParse(response);
      if (checkRequestXml(httpMessage, data)) {
        httpMessage.setData(ClassUtils.mapToObject(data, WxPayMessage.class));
      }
    } catch (Exception e) {
      httpMessage.setCode(WechatMessageCode.BAD_REQUEST_WECHAT_HTTP_ANALYSIS.getCode());
      httpMessage.setMessage(WechatMessageCode.BAD_REQUEST_WECHAT_HTTP_ANALYSIS.getMessage());
      log.error(e.getLocalizedMessage());
      return httpMessage;
    }
    return httpMessage;
  }

  /**
   * 发起微信Https Post请求
   *
   * @param requestUrl 请求地址
   * @param params     请求参数
   * @param clazz      泛型类型
   * @return Message<T>
   */
  public static <T> Message<T> httpsPostWechat(String requestUrl, String params, Class<T> clazz) {
    return httpsRequestWechat(requestUrl, "POST", params, clazz);
  }

  /**
   * 发起微信Https Post请求
   *
   * @param requestUrl 请求地址
   * @param clazz      泛型类型
   * @return Message<T>
   */
  public static <T> Message<T> httpsPostWechat(String requestUrl, Class<T> clazz) {
    return httpsPostWechat(requestUrl, null, clazz);
  }

  /**
   * 发起微信Https Get请求
   *
   * @param requestUrl 请求地址
   * @param params     请求参数
   * @param clazz      泛型类型
   * @return Message<T>
   */
  public static <T> Message<T> httpsGetWechat(String requestUrl, String params, Class<T> clazz) {
    return httpsRequestWechat(requestUrl, "GET", params, clazz);
  }

  /**
   * 发起微信Https Get请求
   *
   * @param requestUrl 请求地址
   * @param clazz      泛型类型
   * @return Message<T>
   */
  public static <T> Message<T> httpsGetWechat(String requestUrl, Class<T> clazz) {
    return httpsGetWechat(requestUrl, null, clazz);
  }
}
