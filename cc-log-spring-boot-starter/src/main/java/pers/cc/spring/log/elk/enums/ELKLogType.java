package pers.cc.spring.log.elk.enums;

/**
 * @author chengce
 * @version 2019-01-05 00:38
 */
public enum ELKLogType {
  /**
   * 没有任何异常
   */
  NONE_EXCEPTION,

  /**
   * web服务器异常
   */
  SERVE_ERROR,

  /**
   * 程序级别异常
   */
  EXCEPTION,

  /**
   * 非中断型异常
   */
  RUNTIME_EXCEPTION,

  /**
   * 自定义异常
   */
  BASE_RUNTIME_EXCEPTION,
}
