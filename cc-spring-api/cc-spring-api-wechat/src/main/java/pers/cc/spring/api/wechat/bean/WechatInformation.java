package pers.cc.spring.api.wechat.bean;

import lombok.Data;

/**
 * 微信基本信息
 *
 * @author chengce
 * @version 2018-06-01 14:44
 */
@Data
public class WechatInformation {
  /**
   * 微信号
   */
  private String id;

  /**
   * AppId
   */
  private String appId;

  /**
   * AppSecret
   */
  private String appSecret;

  /**
   * 商户Id
   */
  private String merchantId;

  /**
   * 商户Key
   */
  private String merchantKey;

  /**
   * 微信自定义token
   */
  private String token;
}
