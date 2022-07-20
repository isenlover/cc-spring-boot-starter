package pers.cc.spring.api.aliyun.sms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云短信发送model
 *
 * @author chengce
 * @version 2018-01-11 12:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AliyunSMSDTO {
  /**
   * 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
   */
  private String mobile;
  /**
   * 必填:短信模板-可在短信控制台中找到
   */
  private String template;
  /**
   * 内容 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为 "{\"name\":\"Tom\", \"code\":\"123\"}"
   */
  private String content;
  /**
   * 附加字段
   */
  private String outId;
}
