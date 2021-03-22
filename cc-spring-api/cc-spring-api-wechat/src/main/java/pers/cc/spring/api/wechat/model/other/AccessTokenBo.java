package pers.cc.spring.api.wechat.model.other;

import java.io.Serializable;

/**
 * Created by CC on 2016-04-28 下午5:45
 */
public class AccessTokenBo extends WxBaseResponse implements Serializable {

    private static AccessTokenBo accessTokenBo;

    /**
     * 取消外部调用实例初始化
     */
    private AccessTokenBo() {

    }

    /**
     * 单例
     *
     * @return 单例
     */
    public static AccessTokenBo getInstance() {
        if (accessTokenBo == null) accessTokenBo = new AccessTokenBo();
        return accessTokenBo;
    }

    public static void setAccessTokenBo(AccessTokenBo accessTokenBo) {
        if (accessTokenBo == null) return;
        AccessTokenBo.accessTokenBo = accessTokenBo;
    }

    /**
     * Token值
     */
    private String access_token;

    /**
     * Token有效期
     */
    private int expires_in;

    /**
     * 错误代码
     */
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
