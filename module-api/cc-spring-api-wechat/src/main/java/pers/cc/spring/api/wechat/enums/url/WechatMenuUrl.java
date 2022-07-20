package pers.cc.spring.api.wechat.enums.url;

/**
 * Created by CC on 2016-11-14 17:16
 */
public enum WechatMenuUrl {

  // 创建普通微信菜单
  createNormalUrl("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"),

  // 获取微信菜单
  getNormalUrl("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN"),

  // 创建自定义微信菜单
  createConditionalUrl("https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN"),

  // 查询所有菜单
  getAllUrl("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN"),

  // 删除个性化菜单
  deleteConditionalUrl("https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN"),

  // 删除所有菜单
  deleteAllUrl("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN"),

  // 查询用户能看到的菜单
  getUserMenusUrl("https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN"),

  ;
  private String url;

  WechatMenuUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
