package pers.cc.spring.core.message;

/**
 * 微信自定义消息枚举
 * 尾数范围【200~299】
 *
 * @author chengce
 * @version 2018-04-30 17:17
 */
public enum WechatMessageCode {
  /**
   * 微信获取用户信息失败
   */
  BAD_REQUEST_WECHAT_USER_FAILED("400200", "获取用户信息失败", 400),
  /**
   * 微信HTTP解析失败
   */
  BAD_REQUEST_WECHAT_HTTP_ANALYSIS("400201", "微信解析失败", 400),
  /**
   * 微信账号登录时间过期
   */
  FORBIDDEN_WECHAT_EXPIRED("403200", "需要重新登录微信获取用户信息", 403),

  ;
  private String code;
  private String message;
  private Integer statusCode;

  WechatMessageCode(String code,
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
