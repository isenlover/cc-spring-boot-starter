package pers.cc.spring.api.wechat.service;


import pers.cc.spring.api.wechat.model.other.AuthenticationBo;

import java.security.NoSuchAlgorithmException;

/**
 * Created by CC on 2016-04-26 下午4:14
 */
public interface WechatCoreService {

  /**
   * 验证微信签名
   *
   * @param authenticationBo 签名businessObject
   * @return 签名验证结果
   */
  boolean checkSignature(AuthenticationBo authenticationBo) throws NoSuchAlgorithmException;

  /**
   * 初始化AccessToken(2小时获取一次)
   * 获取上限: 100000/天
   */
  void initAccessToken();
}
