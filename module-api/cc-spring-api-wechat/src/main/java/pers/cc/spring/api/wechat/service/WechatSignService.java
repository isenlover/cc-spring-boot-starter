package pers.cc.spring.api.wechat.service;

import pers.cc.spring.api.wechat.model.js.WxJsConfigParamVO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;

/**
 * @author chengce
 * @version 2018-06-01 15:24
 */
public interface WechatSignService {
  String createSignAndXml(Object obj, String merchantKey);

  WxJsConfigParamVO createJsSign(String jsapi_ticket, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException;

  boolean checkSignature(String signature, String timestamp, String nonce) throws NoSuchAlgorithmException;

  String createSign(String characterEncoding, SortedMap<Object, Object> parameters, String appSecret);
}
