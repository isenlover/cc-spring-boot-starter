package pers.cc.spring.api.wechat.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.bean.WechatInformation;
import pers.cc.spring.api.wechat.model.js.WxJsConfigParamVO;
import pers.cc.spring.api.wechat.service.WechatSignService;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.core.util.other.DateUtils;
import pers.cc.spring.core.util.other.MD5Utils;
import pers.cc.spring.core.util.other.MathUtils;
import pers.cc.spring.core.util.xml.JdomXmlUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * com.cc.api.wechat.util
 *
 * @author chengce
 * @version 2017-10-26 12:58
 */
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatSignImpl implements WechatSignService {

  @Autowired
  WechatInformation wechatAppBean;

  /**
   * 创建支付相关的签名及需要的Xml
   *
   * @param obj         支付相关object
   * @param merchantKey 商户id
   * @return xml string
   */
  public String createSignAndXml(Object obj, String merchantKey) {
    if (CommonUtils.isEmpty(obj, merchantKey)) {
      return null;
    }
    SortedMap<Object, Object> map = ClassUtils.objectToSortedMap(obj);
    map.put("sign", createSign("utf-8", map, merchantKey));
    // FIXME: 2018/4/30
//        return CCJdomXmlUtil.getRequestXml(map);
    return JdomXmlUtils.getRequestXml(map);
  }

  /**
   * 生成js config所需参数
   *
   * @param jsapi_ticket ticket 通过接口获取
   * @param url          本页面的完整路径
   * @return js所需参数
   * @throws NoSuchAlgorithmException     异常
   * @throws UnsupportedEncodingException 异常
   */
  public WxJsConfigParamVO createJsSign(String jsapi_ticket,
                                        String url) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    Map<String, Object> ret = new HashMap<>();
    String nonce_str = MathUtils.getRandom(16, MathUtils.NonceType.CHAR);
    String timestamp = DateUtils.getTimestamp(TimeUnit.SECONDS).toString();
    String string1;
    String signature;

    //注意这里参数名必须全部小写，且必须有序
    string1 = "jsapi_ticket=" + jsapi_ticket +
        "&noncestr=" + nonce_str +
        "&timestamp=" + timestamp +
        "&url=" + url;

    MessageDigest crypt = MessageDigest.getInstance("SHA-1");
    crypt.reset();
    crypt.update(string1.getBytes("UTF-8"));
    signature = byteToHex(crypt.digest());

    ret.put("url", url);
    ret.put("jsapi_ticket", jsapi_ticket);
    ret.put("nonceStr", nonce_str);
    ret.put("timestamp", timestamp);
    ret.put("signature", signature);

    return ClassUtils.mapToObject(ret, WxJsConfigParamVO.class);
  }


  private String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  /**
   * 验证签名
   *
   * @param signature 签名
   * @param timestamp 时间戳
   * @param nonce     随机数
   * @return 验证结果
   */
  public boolean checkSignature(String signature, String timestamp, String nonce) throws NoSuchAlgorithmException {
    if (CommonUtils.isEmpty(signature, timestamp, nonce)) {
      return false;
    }
    String[] arr = new String[]{wechatAppBean.getToken(), timestamp, nonce};
    // 将token、timestamp、nonce三个参数进行字典序排序
    Arrays.sort(arr);
    StringBuilder content = new StringBuilder();
    for (String anArr : arr) {
      content.append(anArr);
    }
    MessageDigest md;
    String tmpStr;

    md = MessageDigest.getInstance("SHA-1");
    // 将三个参数字符串拼接成一个字符串进行sha1加密
    byte[] digest = md.digest(content.toString().getBytes());
    tmpStr = byteToStr(digest);

    // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
    return tmpStr != null && tmpStr.equals(signature.toUpperCase());
  }

  /**
   * 将字节数组转换为十六进制字符串
   *
   * @param byteArray 字节数组
   * @return 十六进制字符串
   */
  private String byteToStr(byte[] byteArray) {
    String strDigest = "";
    for (byte aByteArray : byteArray) {
      strDigest += byteToHexStr(aByteArray);
    }
    return strDigest;
  }

  /**
   * 将字节转换为十六进制字符串
   *
   * @param mByte 字节数组
   * @return 十六进制字符串
   */
  private String byteToHexStr(byte mByte) {
    char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    char[] tempArr = new char[2];
    tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
    tempArr[1] = Digit[mByte & 0X0F];

    return new String(tempArr);
  }

  /**
   * 统一下单创建签名
   *
   * @param characterEncoding 字符
   * @param parameters        需要签名的map
   * @param appSecret         商户号密匙 32位
   * @return 签名
   */
  public String createSign(String characterEncoding, SortedMap<Object, Object> parameters, String appSecret) {
    StringBuffer sb = new StringBuffer();
    Set es = parameters.entrySet();
    for (Object e : es) {
      Map.Entry entry = (Map.Entry) e;
      String k = (String) entry.getKey();
      Object v = entry.getValue();
      if (null != v && !"".equals(v)
          && !"sign".equals(k) && !"key".equals(k)) {
        sb.append(k).append("=").append(v).append("&");
      }
    }
    sb.append("key=").append(appSecret);
    return MD5Utils.getMD5(sb.toString(), characterEncoding).toUpperCase();
  }
}
