package pers.cc.spring.core.message;

/**
 * 阿里云-OSS自定义消息枚举
 * 尾数范围【500~599】
 *
 * @author chengce
 * @version 2018-04-30 17:22
 */
public enum OssMessageCode {


    ;
    private String code;
    private String message;
    private Integer statusCode;

    OssMessageCode(String code,
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
