package pers.cc.spring.api.wechat.model.user;

import lombok.Data;

/**
 * @author chengce
 * @version 2016-11-17 22:29
 */
@Data
public class UserTagRequest {
    private int tagid;
    // 第一个拉取的OPENID，不填默认从头开始拉取
    private String next_openid;

    public UserTagRequest() {
    }

    public UserTagRequest(int tagid) {
        this.tagid = tagid;
    }

    public UserTagRequest(int tagid, String next_openid) {
        this.tagid = tagid;
        this.next_openid = next_openid;
    }
}
