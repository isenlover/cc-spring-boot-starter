package pers.cc.spring.api.wechat.model.js.oauth;

import lombok.Data;
import pers.cc.spring.core.annotation.typescript.Typescript;

/**
 * 微信js获取用户授权
 * 第一步【传参】
 *
 * @author chengce
 * @version 2017-10-26 14:57
 */
@Data
@Typescript
public class WxJsOAuthDTO {
    private String appid;

    private String redirect_uri;

    private String response_type;

    private WxJsOAuthScopeType scope;

    private String state;
}
