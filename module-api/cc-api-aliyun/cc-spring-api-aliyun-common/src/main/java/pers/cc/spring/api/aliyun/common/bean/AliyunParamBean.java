package pers.cc.spring.api.aliyun.common.bean;

import lombok.Data;

/**
 * @author chengce
 * @version 2021-03-23 18:12
 */
@Data
public class AliyunParamBean {
  /**
   * 阿里云账号 accessKeyId
   */
  private String key;

  /**
   * 阿里云账号 accessKeySecret
   */
  private String secret;
}
