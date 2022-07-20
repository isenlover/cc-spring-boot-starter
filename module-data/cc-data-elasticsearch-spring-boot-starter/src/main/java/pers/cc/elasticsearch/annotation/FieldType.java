package pers.cc.elasticsearch.annotation;

/**
 * 常见的字段类型
 *
 * @author chengce
 * @version 2018-07-06 15:48
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html"></a>
 */
public enum FieldType {
    // string
    Text,
    keyword,
    // Numeric datatypes
    Integer,
    Long,
    Float,
    Double,
    // Boolean datatype
    Boolean,
    Date,
    Object,
    Auto,
    Nested,
    Ip,;

    private FieldType() {
    }
}
