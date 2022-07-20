package pers.cc.spring.api.wechat.model.user;

import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * Created by CC on 2016-11-14 17:53
 */
public class TagMessage extends WxBaseResponse {
    private Tag tag;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
