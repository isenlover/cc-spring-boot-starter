package pers.cc.spring.api.wechat.enums;

/**
 * Created by iSen on 2016/11/10
 */
public enum WechatMessageType {
    /**
     * 回复文本
     */
    response_text("text"),

    /**
     * 回复音乐
     */
    response_music("music"),

    /**
     * 回复新闻
     */
    response_news("news"),

    /**
     * 请求文本
     */
    request_text("text"),

    /**
     * 请求图片
     */
    request_image("image"),

    /**
     * 请求链接
     */
    request_link("link"),

    /**
     * 请求声音
     */
    request_voice("voice"),

    /**
     * 请求事件
     */
    request_event("event"),

    /**
     * 订阅事件
     */
    event_subscribe("subscribe"),

    /**
     * 取消订阅事件
     */
    event_unsubscribe("unsubscribe"),

    /**
     * 点击事件
     */
    event_CLICK("CLICK"),

    /**
     * 上报位置
     */
    event_Location("LOCATION")
    
    ;


    private String type;

    WechatMessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
