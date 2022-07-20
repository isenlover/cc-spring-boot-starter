package pers.cc.elasticsearch.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author chengce
 * @version 2018-07-05 18:31
 */
@Data
@ConfigurationProperties(prefix = "cc.elasticsearch.rest")
public class ElasticsearchProperties {

    /**
     * 多个ip逗号分隔
     */
    private String clusterNodes = "127.0.0.1";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时相关配置
     */
    @NestedConfigurationProperty
    private ElasticsearchTimeout timeout = new ElasticsearchTimeout();
}
