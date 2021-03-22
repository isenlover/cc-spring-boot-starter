package pers.cc.spring.core.message;

/**
 * 网易通信云自定义消息枚举
 * 尾数范围【300~399】
 *
 * @author chengce
 * @version 2018-04-30 17:20
 */
public enum NimMessageCode {
    // 400100开始为网易第三方错误码
    /**
     * 网易云信创建用户失败
     */
    BAD_REQUEST_NIM_USER_CREATE("400300", "网易云信创建用户失败", 400),
    ;
    private String code;
    private String message;
    private Integer statusCode;

    NimMessageCode(String code,
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
