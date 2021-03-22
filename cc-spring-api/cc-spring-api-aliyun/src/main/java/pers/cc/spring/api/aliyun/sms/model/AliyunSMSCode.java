package pers.cc.spring.api.aliyun.sms.model;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * 阿里云短信Model
 *
 * @author chengce
 * @version 2017-10-11 19:52
 */
public class AliyunSMSCode extends AliyunSMSBase {
  /**
   * 验证码
   */
  private String code;

  public static AliyunSMSCode builder(SendSmsResponse sendSmsResponse) {
    AliyunSMSCode aliyunSMSCode = new AliyunSMSCode();
    aliyunSMSCode.setSendSmsResponse(sendSmsResponse);
    return aliyunSMSCode;
  }

  public AliyunSMSCode mobile(String mobile) {
    this.setMobile(mobile);
    return this;
  }

  public AliyunSMSCode code(String code) {
    this.setCode(code);
    return this;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
