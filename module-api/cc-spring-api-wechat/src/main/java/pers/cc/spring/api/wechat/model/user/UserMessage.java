package pers.cc.spring.api.wechat.model.user;

import lombok.Data;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * Created by iSen on 2016/11/10
 * subscribe	用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
 * openid	用户的标识，对当前公众号唯一
 * nickname	用户的昵称
 * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
 * city	用户所在城市
 * country	用户所在国家
 * province	用户所在省份
 * language	用户的语言，简体中文为zh_CN
 * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
 * subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
 * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
 * remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
 * groupid	用户所在的分组ID（兼容旧的用户分组接口）
 * tagid_list	用户被打上的标签ID列表
 */
@Data
public class UserMessage extends WxBaseResponse {
    private int subscribe;
    private String openid;
    // 用于查询用户下的菜单
    private String user_id;
    private String nickname;
    private int sex;
    private String city;
    private String country;
    private String language;
    private String headimgurl;
    private long subscribe_time;
    private String unionid;
    private String remark;
    private int groupid;
    private String tagid_list;

    public UserMessage() {
    }

    public UserMessage(String openid) {
        this.openid = openid;
    }

    public UserMessage(String user_id, String openid) {
        this.user_id = user_id;
        this.openid = openid;
    }
}
