package pers.cc.spring.api.wechat.enums.url;

/**
 * @author chengce
 * @version 2016-12-22 21:17
 */
public enum WechatQRCodeUrl {

  // 获取二维码
  getUrl("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN"),

  // 获取二维码图片
  getImageUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET"),

  ;
  private String url;

  WechatQRCodeUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}