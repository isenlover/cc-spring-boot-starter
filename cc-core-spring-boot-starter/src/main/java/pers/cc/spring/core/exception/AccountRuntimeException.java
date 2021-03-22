package pers.cc.spring.core.exception;


import pers.cc.spring.core.message.MessageCode;

/**
 * 账号异常
 *
 * @author chengce
 * @version 2017-12-30 21:36
 */
public class AccountRuntimeException extends BaseRuntimeException {
    public AccountRuntimeException() {
        super(MessageCode.BAD_REQUEST_USERNAME_ERROR);
    }

    public AccountRuntimeException(MessageCode messageCode) {
        super(messageCode);
    }

    public AccountRuntimeException(String message, MessageCode messageCode) {
        super(message, messageCode);
    }
}
