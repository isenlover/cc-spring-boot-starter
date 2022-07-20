package pers.cc.spring.api.wechat.enums.url;

/**
 * @author chengce
 * createTime 2016-11-17 17:41
 */
public enum WechatTagUrl {

    // 创建标签
    createUrl("https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN"),

    // 获取标签列表
    getUrl("https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN"),

    // 编辑标签
    editUrl("https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN"),

    // 删除标签
    deleteUrl("https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN"),

    // 获取标签下粉丝列表
    getFansListTagUrl("https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN"),


    ;
    private String url;

    WechatTagUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
