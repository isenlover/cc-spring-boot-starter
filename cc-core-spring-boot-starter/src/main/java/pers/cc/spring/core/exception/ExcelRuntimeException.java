package pers.cc.spring.core.exception;

import pers.cc.spring.core.message.MessageCode;

/**
 * Excel相关异常
 *
 * @author chengce
 * @version 2017-12-30 23:14
 */
public class ExcelRuntimeException extends BaseRuntimeException {

  public ExcelRuntimeException(String message, MessageCode messageCode) {
    super(message, messageCode);
  }

  public ExcelRuntimeException(MessageCode messageCode) {
    super(messageCode);
  }

}
