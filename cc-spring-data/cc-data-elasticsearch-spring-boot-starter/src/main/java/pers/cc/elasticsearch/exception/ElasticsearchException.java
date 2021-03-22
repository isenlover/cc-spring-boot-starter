package pers.cc.elasticsearch.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.MessageCode;

/**
 * @author chengce
 * @version 2018-07-09 15:43
 */
public class ElasticsearchException extends BaseRuntimeException {
    public ElasticsearchException(String message, MessageCode messageCode) {
        super(message, messageCode);
    }

    public ElasticsearchException(String message, String errCode, int statusCode) {
        super(message, errCode, statusCode);
    }

    public ElasticsearchException(String message) {
        super(message);
    }
}
