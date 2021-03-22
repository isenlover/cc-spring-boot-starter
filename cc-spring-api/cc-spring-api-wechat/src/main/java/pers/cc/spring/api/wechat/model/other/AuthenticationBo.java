package pers.cc.spring.api.wechat.model.other;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by CC on 2016-04-26 下午4:20
 */
@Data
public class AuthenticationBo implements Serializable {
    /*微信加密签名*/
    private String signature;
    /*时间戳*/
    private String timestamp;
    /*随机数*/
    private String nonce;
    /*随机字符串*/
    private String echostr;
}
