package pers.cc.spring.core.exception;

import pers.cc.spring.core.message.MessageCode;

/**
 * 两个类类型或值不一致，抛出此异常
 *
 * @author chengce
 * @version 2017-10-16 22:46
 */
public class ClassDifferentRuntimeException extends BaseRuntimeException {
    public ClassDifferentRuntimeException() {
        super(MessageCode.SERVER_ERROR_CLASS_DIFFERENT_EXCEPTION);
    }

    public ClassDifferentRuntimeException(String message) {
        super(message);
    }
}
