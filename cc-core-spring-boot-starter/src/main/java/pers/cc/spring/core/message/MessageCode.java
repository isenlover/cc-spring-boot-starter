package pers.cc.spring.core.message;

/**
 * Http 出现异常或bad request或其他错误消息及代码枚举
 * 尾数【1~199】
 * 不同项目自定义code从 1000开始，例如4031000
 *
 * @author chengce
 * @version 2017-12-30 21:06
 */
public enum MessageCode {
    /* 401 */
    /**
     * 没有授权，需要重新登录
     */
    UNAUTHORIZED("4010", "没有授权", 401),
    /**
     * 账号被冻结
     */
    UNAUTHORIZED_FREEZE("4011", "账号已被冻结", 401),
    /**
     * 账号登录时间已过期
     */
    UNAUTHORIZED_EXPIRED("4012", "登录已过期,需重新登录", 401),

    /* 403 */
    /**
     * 没有权限访问
     */
    FORBIDDEN_PERMISSION("4030", "没有权限", 403),

    /* 400 */
    /**
     * 账号已存在
     */
    BAD_REQUEST_USERNAME_EXIST("4000", "账号已存在", 400),
    /**
     * 短信根据本地项目设置
     * 超出了本地项目发送频率触发
     */
    BAD_REQUEST_SMS_LIMIT_LOCAL("4001", "短信发送过于频繁", 400),
    /**
     * api接口请求超出限制
     */
    BAD_REQUEST_REQUEST_LIMIT("4002", "稍微冷静下，太频繁了", 400),
    /**
     * controller 参数不合法
     */
    BAD_REQUEST_PARAM("4003", "字段不合法", 400),
    /**
     * 极验验证码滑块未通过或过期
     */
    BAD_REQUEST_GEETEST("4004", "请先通过验证滑块", 400),
    /**
     * 账号或密码错误
     */
    BAD_REQUEST_USERNAME_ERROR("4005", "账号或密码错误", 400),
    /**
     * 账号不存在
     */
    BAD_REQUEST_USERNAME_NOT_EXIST("4006", "账号不存在", 400),
    /**
     * 唯一索引存储失败
     */
    BAD_REQUEST_CREATE_ERROR_UNIQUE_KEY("4008", "存储失败", 400),

    /* 404 */
    /**
     * 页面不存在
     */
    PAGE_NOT_FOUND("404", "访问内容不存在", 404),

    /* 500 */
    /**
     * 服务器异常
     */
    SERVER_ERROR("5000", "服务器异常", 500),
    /**
     * 两个类类型或值不一致
     */
    SERVER_ERROR_CLASS_DIFFERENT_EXCEPTION("5001", "服务器内部异常", 500),
    /**
     * Excel解析异常
     */
    SERVER_ERROR_EXCEL("5002", "解析出错", 500),
    /**
     * base64图片格式有误
     */
    SERVER_ERROR_BASE64("5003", "base64解析出错", 500),
    /**
     * 缓存获/存取异常
     */
    SERVER_ERROR_REDIS_EXCEPTION("5004", "缓存获/存取异常", 500),
    /**
     * 缓存key不合法
     */
    SERVER_ERROR_REDIS_KEY_EXCEPTION("5005", "缓存key不合法", 500),
    /**
     * 拼音解析出错
     */
    SERVER_ERROR_PIN_YIN_EXCEPTION("5006", "拼音解析出错", 500),
    /**
     * No handler found
     */
    SERVER_ERROR_URL_ERROR("5007", "请求地址异常", 500),
    /**
     * HttpMessageNotReadableException
     */
    SERVER_ERROR_PARAM_PARSE_ERROR("5008", "参数异常", 500),


    // 系统自带异常的处理 500100开始
    /**
     * IOException
     */
    SERVER_ERROR_IO_EXCEPTION("500100", "", 500),

    ;

    private String code;
    private String message;
    private Integer statusCode;

    MessageCode(String code,
                String message,
                Integer statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
