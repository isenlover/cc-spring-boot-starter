package pers.cc.elasticsearch.property;

import lombok.Data;

/**
 * rest client 超时相关配置
 *
 * @author chengce
 * @version 2018-07-05 18:41
 */
@Data
public class ElasticsearchTimeout {
    /**
     * 连接超时时间
     */
    private int connectTimeout = 3000;

    /**
     * socket通信超时
     */
    private int socketTimeout = 30000;

    /**
     * 连接池获取到连接的超时时间
     */
    private int connectRequestTimeout = 3000;

    /**
     * 最大重试超时
     */
    private int maxRetryTimeout = 60000;
}
