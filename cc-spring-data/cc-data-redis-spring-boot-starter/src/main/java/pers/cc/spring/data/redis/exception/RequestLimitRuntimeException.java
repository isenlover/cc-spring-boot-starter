package pers.cc.spring.data.redis.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.MessageCode;

/**
 * 请求超过限制异常
 *
 * @author chengce
 * @version 2018-05-10 12:08
 */
public class RequestLimitRuntimeException extends BaseRuntimeException {
    public RequestLimitRuntimeException() {
        super(MessageCode.BAD_REQUEST_REQUEST_LIMIT);
    }

    public RequestLimitRuntimeException(String message, String errCode, int statusCode) {
        super(message, errCode, statusCode);
    }

    public RequestLimitRuntimeException(String message) {
        super(message);
    }

    public RequestLimitRuntimeException(String message, MessageCode messageCode) {
        super(message, messageCode);
    }

    public RequestLimitRuntimeException(MessageCode messageCode) {
        super(messageCode);
    }
}
