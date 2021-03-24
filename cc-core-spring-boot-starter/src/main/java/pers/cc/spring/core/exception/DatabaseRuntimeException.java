package pers.cc.spring.core.exception;

/**
 * 数据库相关异常
 *
 * @author chengce
 * @version 2018-03-24 00:14
 */
public class DatabaseRuntimeException extends BaseRuntimeException {

  public DatabaseRuntimeException(String message) {
    super(message);
  }
}
