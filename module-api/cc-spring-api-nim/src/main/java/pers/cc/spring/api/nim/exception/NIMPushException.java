package pers.cc.spring.api.nim.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.MessageCode;

/**
 * 网易推送异常
 *
 * @author chengce
 * @version 2018-01-08 14:53
 */
public class NIMPushException extends BaseRuntimeException {

  public NIMPushException() {
    super("推送失败");
  }

  public NIMPushException(String message, MessageCode errCode) {
    super(message, errCode);
  }

}
