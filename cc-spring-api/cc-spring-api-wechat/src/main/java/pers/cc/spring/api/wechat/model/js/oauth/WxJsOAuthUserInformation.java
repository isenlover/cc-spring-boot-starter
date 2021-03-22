package pers.cc.spring.api.wechat.model.js.oauth;

import lombok.Data;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * 微信js获取用户授权
 * 第三步【参数返回】
 *
 * @author chengce
 * @version 2017-11-13 20:28
 */
@Data
public class WxJsOAuthUserInformation extends WxBaseResponse {
    private String openid;

    private String nickname;

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex;

    private String province;

    private String city;

    /**
     * 国家，如中国为CN
     */
    private String country;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String headimgurl;

    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     */
    private String privilege;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;
}
