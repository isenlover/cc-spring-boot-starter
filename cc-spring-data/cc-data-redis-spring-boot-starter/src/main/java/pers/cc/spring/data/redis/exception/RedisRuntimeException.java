package pers.cc.spring.data.redis.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.MessageCode;

/**
 * 缓存异常类
 *
 * @author chengce
 * @version 2017-12-12 22:44
 */
public class RedisRuntimeException extends BaseRuntimeException {

  public RedisRuntimeException() {
    super(MessageCode.SERVER_ERROR_REDIS_EXCEPTION);
  }

  public RedisRuntimeException(MessageCode messageCode) {
    super(messageCode);
  }

  public RedisRuntimeException(String message, String errCode, int statusCode) {
    super(message, errCode, statusCode);
  }

  public RedisRuntimeException(String message) {
    super(message);
  }
}
