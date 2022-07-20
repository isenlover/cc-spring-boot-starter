package pers.cc.spring.api.nim.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;

/**
 * 网易云聊天室创建异常
 *
 * @author chengce
 * @version 2018-01-07 03:09
 */
public class NIMChatroomCreateException extends BaseRuntimeException {

    public NIMChatroomCreateException() {
        super("创建聊天室失败");
    }

}
