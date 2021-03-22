package pers.cc.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-07-09 00:42
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/index-modules.html"></a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Document {
    String indexName();

    String type() default "";

    short shards() default 5;

    short replicas() default 1;

    String refreshInterval() default "1s";

    IndexStoreType indexStoreType() default IndexStoreType.fs;

    /**
     * 是否自动创建索引
     */
    boolean createIndex() default true;
}
