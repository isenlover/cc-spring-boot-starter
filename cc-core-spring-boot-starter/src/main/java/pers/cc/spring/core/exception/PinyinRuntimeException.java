package pers.cc.spring.core.exception;

import pers.cc.spring.core.message.MessageCode;

/**
 * @author chengce
 * @version 2018-06-03 16:59
 */
public class PinyinRuntimeException extends BaseRuntimeException {

  public PinyinRuntimeException(String message) {
    super(message, MessageCode.SERVER_ERROR_PIN_YIN_EXCEPTION);
  }
}
