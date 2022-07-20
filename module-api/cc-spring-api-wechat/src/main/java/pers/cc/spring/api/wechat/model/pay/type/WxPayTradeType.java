package pers.cc.spring.api.wechat.model.pay.type;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay
 *
 * 微信支付类型
 * @author chengce
 * @version 2017-10-25 15:00
 */
public enum WxPayTradeType {
    JSAPI,   // 公众号
    NATIVE,  // 原生扫码支付
    APP;     // app支付


    @Override
    public String toString() {
        return this.name();
    }
}
