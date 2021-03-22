package pers.cc.spring.core.message;

/**
 * 阿里云-短信自定义消息枚举
 * 尾数范围【400~499】
 *
 * @author chengce
 * @version 2018-04-30 17:22
 */
public enum SmsMessageCode {

    /**
     * 阿里云短信发送失败，超出官方限制
     */
    BAD_REQUEST_SMS_LIMIT("400400", "短信发送失败，超出系统设置限制", 400),

    /**
     * 验证码错误
     */
    BAD_REQUEST_SMS_ERROR("400401", "验证码错误", 400),

    ;

    private String code;
    private String message;
    private Integer statusCode;

    SmsMessageCode(String code,
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
