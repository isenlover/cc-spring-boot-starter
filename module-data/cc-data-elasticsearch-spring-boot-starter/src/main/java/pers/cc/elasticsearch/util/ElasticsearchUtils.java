package pers.cc.elasticsearch.util;

import pers.cc.elasticsearch.annotation.DateFormat;
import pers.cc.elasticsearch.annotation.Document;
import pers.cc.elasticsearch.exception.ElasticsearchException;
import pers.cc.elasticsearch.exception.ElasticsearchExceptionConstant;
import pers.cc.spring.core.util.other.ClassUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chengce
 * @version 2018-07-09 00:48
 */
public class ElasticsearchUtils {

    /**
     * 自动获取字段的映射类型
     *
     * @param field 字段
     * @return 映射类型
     */
    public static String getAutoFieldType(Field field) {
        Class typeClass = field.getType();
        boolean isBaseType = typeClass.equals(Integer.class) || typeClass.equals(int.class) || typeClass.equals(
                Long.class) || typeClass.equals(long.class) || typeClass.equals(Double.class) || typeClass.equals(
                double.class) || typeClass.equals(short.class) || typeClass.equals(Float.class) || typeClass.equals(
                float.class) || typeClass.equals(Date.class) || typeClass.equals(boolean.class) || typeClass.equals(
                Boolean.class);
        if (isBaseType) {
            return typeClass.getSimpleName().toLowerCase();
        } else {
            return "text";
        }
    }

    /**
     * 获取日期格式化列表
     *
     * @param formats 原始格式化
     * @return 日期格式化列表
     */
    public static String getFormats(DateFormat[] formats) {
        Stream<DateFormat> stream = Arrays.stream(formats);
        return stream.map(s -> {
            switch (s) {
                case custom_year:
                    return "yyyy年";
                case custom_year_month:
                    return "yyyy年MM月";
                case custom_date:
                    return "yyyy年MM月dd日";
                case custom_date_time:
                    return "yyyy年MM月dd日 hh:mm:ss";
            }
            if (s.equals(DateFormat.custom)) {
                return "";
            }
            return s.name();
        }).collect(Collectors.joining("||"));
    }

    /**
     * 获取类注解索引名称
     *
     * @param indexClass 类
     * @return 索引名称
     */
    public static String getIndexName(Class indexClass) {
        if (ClassUtils.hasAnnotation(indexClass, Document.class)) {
            Document document = ClassUtils.getAnnotation(indexClass, Document.class);
            return document.indexName();
        } else {
            return null;
        }
    }

    /**
     * 获取类注解type名称
     *
     * @param indexClass 类
     * @return type名称
     */
    public static String getTypeName(Class indexClass) {
        if (ClassUtils.hasAnnotation(indexClass, Document.class)) {
            Document document = ClassUtils.getAnnotation(indexClass, Document.class);
            return document.indexName();
        } else {
            return null;
        }
    }

    /**
     * 获取Document注解实例,如果不存在抛出异常
     *
     * @param documentClass 类
     * @return document
     */
    public static Document getDocument(Class documentClass) {
        Document document = ClassUtils.getAnnotation(documentClass, Document.class);
        if (document == null) {
            throw new ElasticsearchException(ElasticsearchExceptionConstant.DOCUMENT_NOT_EXIST);
        }
        return document;
    }
}
