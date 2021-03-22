package pers.cc.spring.data.elasticsearch.exception;

import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.MessageCode;

/**
 * Elasticsearch 异常
 *
 * @author chengce
 * @version 2018-04-25 14:25
 */
public class ElasticsearchRuntimeException extends BaseRuntimeException {
    public ElasticsearchRuntimeException(MessageCode messageCode) {
        super(messageCode);
    }

    public ElasticsearchRuntimeException(String message, String errCode, int statusCode) {
        super(message, errCode, statusCode);
    }

    public ElasticsearchRuntimeException(String message) {
        super(message);
    }
}
