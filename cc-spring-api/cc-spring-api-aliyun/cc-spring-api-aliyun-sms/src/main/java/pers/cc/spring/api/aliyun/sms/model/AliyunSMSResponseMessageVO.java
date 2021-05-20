package pers.cc.spring.api.aliyun.sms.model;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云短信基础类
 *
 * @author chengce
 * @version 2018-01-11 16:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliyunSMSResponseMessageVO {
  /**
   * 手机号
   */
  private String mobile;

  /**
   * 时间戳
   */
  private Long timestamp;

  /**
   * 短信回复类
   */
  private SendSmsResponse sendSmsResponse;
}
