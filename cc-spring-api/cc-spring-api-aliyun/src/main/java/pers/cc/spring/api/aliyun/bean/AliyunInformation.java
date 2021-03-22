package pers.cc.spring.api.aliyun.bean;

import lombok.Data;

/**
 * @author chengce
 * @version 2018-06-14 22:40
 */
@Data
public class AliyunInformation {
  /**
   * 阿里云账号 accessKeyId
   */
  private String key;

  /**
   * 阿里云账号 accessKeySecret
   */
  private String secret;
}
