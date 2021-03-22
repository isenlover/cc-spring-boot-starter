package pers.cc.spring.api.wechat.enums;

/**
 * Created by CC on 2016-04-26 下午4:37
 */
public enum WechatKey {
  // 思聚网络微信token值
  TOKEN("SiJuNetWeChatToken"),

  /**
   * 微信基本Url
   */
  // token
  ACCESS_TOKEN("access_token", "ACCESS_TOKEN"),

  // token有效期
  EXPIRES_IN("expires_in"),

  // 微信appID
  APPID("APPID", ""),

  // 微信app secretKey
  APPSECRET("APPSECRET", ""),

  // 获取ACCESS TOKEN URL
  accessTokenUrl("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"),
  ;

  private String name;
  private String value;

  WechatKey(String name) {
    this.name = name;
  }

  WechatKey(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
