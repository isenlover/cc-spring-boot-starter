package pers.cc.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-07-06 15:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Field {
    /**
     * 字段映射类型，默认自动识别
     */
    FieldType type() default FieldType.Auto;

    /**
     * 当字段类型为Date时
     */
    DateFormat[] format() default {};

    /**
     * 当字段类型为text时
     * 是否生成Keyword类型的fields参数
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/multi-fields.html"></a>
     */
    boolean fieldsKeyword() default true;

    /**
     * 当format为custom时
     */
    String dateFormatPattern() default "";

    boolean index() default true;

    boolean store() default false;

    boolean fielddata() default false;

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-boost.html"></a>
     */
    float boost() default 1.0f;

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-analyzer.html"></a>
     */
    String searchAnalyzer() default "";

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/analyzer.html"></a>
     */
    String analyzer() default "";
//
//    String[] ignoreFields() default {};
//
//    boolean includeInParent() default false;
}
