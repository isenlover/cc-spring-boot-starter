package pers.cc.spring.api.wechat.model.pay.response;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay
 * 发起微信支付后返回结果，此结果用于返回app/web等进行支付调用
 * 注：非支付结果类
 *
 * @author chengce
 * @version 2017-10-25 14:57
 */
public class WxPayMessage extends WxPayBaseMessage {
    // 以下字段在return_code为SUCCESS的时候有返回
    /**
     * 调用接口提交的应用ID
     */
    private String appid;
    /**
     * 调用接口提交的商户号
     */
    private String mch_id;
    /**
     * 调用接口提交的终端设备号，
     */
    private String device_info;
    /**
     * 微信返回的随机字符串
     */
    private String nonce_str;
    /**
     * 微信返回的签名
     */
    private String sign;
    /**
     * SUCCESS/FAIL
     */
    private String result_code;
    /**
     * 详细参见第6节错误列表
     */
    private String err_code;
    /**
     * 错误返回的信息描述
     */
    private String err_code_des;

    // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
    /**
     * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP
     */
    private String trade_type;

    /**
     * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    private String prepay_id;

    /**
     * trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付
     */
    private String code_url;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
