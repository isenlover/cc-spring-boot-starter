package pers.cc.elasticsearch.exception;

/**
 * @author chengce
 * @version 2018-07-10 21:05
 */
public class ElasticsearchExceptionConstant {

    public static final String OPERATING_CREATE = "创建";
    public static final String OPERATING_UPDATE = "更新";
    public static final String OPERATING_SELECT = "查询";
    public static final String OPERATING_DELETE = "删除";
    public static final String FAILURE = "索引失败";

    public static final String EXCEPTION_CREATE = OPERATING_CREATE + FAILURE;
    public static final String EXCEPTION_UPDATE = OPERATING_UPDATE + FAILURE;
    public static final String EXCEPTION_SELECT = OPERATING_SELECT + FAILURE;
    public static final String EXCEPTION_DELETE = OPERATING_DELETE + FAILURE;
    public static final String DOCUMENT_NOT_EXIST = "缺失必要的文档参数，请联系管理员处理";
}
