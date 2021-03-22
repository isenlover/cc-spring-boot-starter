package pers.cc.spring.core.exception;

import pers.cc.spring.core.message.MessageCode;

/**
 * 图片相关异常
 *
 * @author chengce
 * @version 2018-01-02 23:00
 */
public class ImageRuntimeException extends BaseRuntimeException {

    public ImageRuntimeException(MessageCode messageCode) {
        super(messageCode);
    }

    public ImageRuntimeException(String message, MessageCode messageCode) {
        super(message, messageCode);
    }
}
