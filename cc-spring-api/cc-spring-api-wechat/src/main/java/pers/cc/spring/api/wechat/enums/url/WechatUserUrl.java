package pers.cc.spring.api.wechat.enums.url;

/**
 * Created by CC on 2016-11-14 17:16
 */
public enum WechatUserUrl {

    // 用户基本信息
    getBaseInfoUrl("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"),

    // 编辑用户备注名
    editUserRemarkUrl("https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN"),

    // 拉去关注用户列表，每次最多10000人，超过10000人可填写next_openid
    getUserListUrl("https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID"),

    ;
    private String url;

    WechatUserUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
