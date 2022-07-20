package pers.cc.spring.api.wechat.model.user;

import lombok.Data;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * @author chengce
 * @version 2016-11-17 22:29
 */
@Data
public class UserTagResponseMessage extends WxBaseResponse {
    private int count = 0;
    private UserData data;
    // 拉取列表最后一个用户的openid
    private String next_openid;
}
