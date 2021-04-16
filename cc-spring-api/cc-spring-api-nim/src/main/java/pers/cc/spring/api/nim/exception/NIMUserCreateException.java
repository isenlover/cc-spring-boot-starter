package pers.cc.spring.api.nim.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;

/**
 * 网易云用户创建异常
 *
 * @author chengce
 * @version 2017-12-27 18:06
 */
public class NIMUserCreateException extends BaseRuntimeException {
  public NIMUserCreateException() {
    super("创建用户失败");
  }

}
