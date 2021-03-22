package pers.cc.spring.core.exception;

import pers.cc.spring.core.message.*;

/**
 * 自定义runtime异常
 *
 * @author chengce
 * @version 2017-12-30 21:00
 */
public class BaseRuntimeException extends RuntimeException {
  /**
   * 异常状态码
   */
  private String errCode;
  /**
   * 异常消息
   */
  private String errMsg;
  /**
   * http 状态码
   * 默认400
   */
  private int statusCode;

  private BaseRuntimeException() {
  }

  public BaseRuntimeException(MessageCode messageCode) {
    super();
    this.errMsg = messageCode.getMessage();
    this.errCode = messageCode.getCode();
    this.statusCode = messageCode.getStatusCode();
  }

  public BaseRuntimeException(NimMessageCode messageCode) {
    super();
    this.errMsg = messageCode.getMessage();
    this.errCode = messageCode.getCode();
    this.statusCode = messageCode.getStatusCode();
  }

  public BaseRuntimeException(OssMessageCode messageCode) {
    super();
    this.errMsg = messageCode.getMessage();
    this.errCode = messageCode.getCode();
    this.statusCode = messageCode.getStatusCode();
  }

  public BaseRuntimeException(SmsMessageCode messageCode) {
    super();
    this.errMsg = messageCode.getMessage();
    this.errCode = messageCode.getCode();
    this.statusCode = messageCode.getStatusCode();
  }


  public BaseRuntimeException(WechatMessageCode messageCode) {
    super();
    this.errMsg = messageCode.getMessage();
    this.errCode = messageCode.getCode();
    this.statusCode = messageCode.getStatusCode();
  }

  public BaseRuntimeException(String message, MessageCode messageCode) {
    super();
    this.errMsg = message;
    this.errCode = messageCode.getCode();
    this.statusCode = messageCode.getStatusCode();
  }

  public BaseRuntimeException(String message, String errCode, int statusCode) {
    super(message);
    this.errMsg = message;
    this.errCode = errCode;
    this.statusCode = statusCode;
  }

  public BaseRuntimeException(String message) {
    super(message);
    this.errMsg = message;
    this.errCode = "-1";
    this.statusCode = 400;
  }


  @Override
  public String getMessage() {
    return this.errMsg;
  }

  public String getErrCode() {
    return errCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
