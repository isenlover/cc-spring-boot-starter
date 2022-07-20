package pers.cc.spring.api.wechat.model.js.oauth;

import pers.cc.spring.core.annotation.typescript.Typescript;

/**
 * 微信js获取用户授权方式
 *
 * @author chengce
 * @version 2018-10-10 16:28
 */
@Typescript
public enum WxJsOAuthScopeType {
    snsapi_base,        //静默获取用户openid
    snsapi_userinfo     //需要用户授权，可以获取用户信息
}
