package pers.cc.spring.api.aliyun.sms.model;

import lombok.Data;

/**
 * 阿里云短信Model
 *
 * @author chengce
 * @version 2017-10-11 19:52
 */
@Data
public class AliyunSMSCodeVO extends AliyunSMSResponseMessageVO {
  /**
   * 验证码
   */
  private String code;
}
