package pers.cc.spring.api.wechat.model.pay.param;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay.client
 * 返回JSAPI支付模式的参数
 *
 * @author chengce
 * @version 2017-10-26 12:54
 */
public class WxJsPayParameter {
    private String appId;

    private String timeStamp;

    private String nonceStr;

    private String signType;

    private String Package;

    private String paySign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    @JSONField(name = "package")
    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = "prepay_id=" + aPackage;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
