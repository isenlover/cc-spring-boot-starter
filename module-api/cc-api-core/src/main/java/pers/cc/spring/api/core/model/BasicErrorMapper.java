package pers.cc.spring.api.core.model;

import lombok.Data;
import pers.cc.spring.core.message.MessageCode;

/**
 * 用于把Map类型的异常转换成java bean
 *
 * @author chengce
 * @version 2018-03-07 12:18
 */
@Data
public class BasicErrorMapper {
  /**
   * 状态码例如 400、500
   */
  private Integer status;
  /**
   * 异常信息
   */
  private String exception = "";
  /**
   * 错误
   */
  private String error;
  /**
   * 自定义消息
   */
  private String message;
  /**
   * 自定义
   * 可以用于判断错误类型
   */
  private String code;

  /**
   * 请求path
   */
  private String path;

  /**
   * 快速存储
   *
   * @param messageCode 消息
   */
  public void messageCode(MessageCode messageCode) {
    this.setCode(messageCode.getCode());
    this.setMessage(messageCode.getMessage());
    this.setStatus(messageCode.getStatusCode());
  }
}
