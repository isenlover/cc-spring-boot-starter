package pers.cc.spring.api.wechat.model.user;

import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * @author chengce
 *         createTime 2016-11-17 17:47
 */
public class TagListMessage extends WxBaseResponse {
    private Tag[] tags;

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }
}
