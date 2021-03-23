package pers.cc.spring.api.aliyun.sms.bean;

import lombok.Data;

/**
 * @author chengce
 * @version 2018-06-14 22:31
 */
@Data
public class AliyunSMSParamBean {
  /**
   * 短信签名
   */
  private String sign;

  /**
   * 短信模板
   */
  private String template;

  /**
   * 短信参数
   */
  private String param;

  /**
   * 过期时间【自定义，非阿里云】
   */
  private int expiryTime;

  /**
   * 超时时间【自定义，非阿里云】
   */
  private int timeout;
}
