package pers.cc.spring.api.aliyun.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.SmsMessageCode;

/**
 * @author chengce
 * @version 2018-06-14 23:02
 */
public class AliyunRuntimeException extends BaseRuntimeException {
  public AliyunRuntimeException() {
    super(SmsMessageCode.BAD_REQUEST_SMS_LIMIT);
  }

  public AliyunRuntimeException(SmsMessageCode messageCode) {
    super(messageCode);
  }

  public AliyunRuntimeException(String message) {
    super(message);
  }
}
