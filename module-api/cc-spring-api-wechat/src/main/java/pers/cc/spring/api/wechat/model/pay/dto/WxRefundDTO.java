package pers.cc.spring.api.wechat.model.pay.dto;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay.dto
 *
 * @author chengce
 * @version 2017-10-26 13:52
 */
public class WxRefundDTO {
    /**
     * 微信分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 随机字符串，不长于32位
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String sign_type;
    /**
     * 微信生成的订单号，在支付通知中有返回
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String out_trade_no;
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    private String out_refund_no;
    /**
     * 订单总金额，单位为分，只能为整数
     */
    private String total_fee;
    /**
     * 退款总金额，订单总金额，单位为分，只能为整数
     */
    private String refund_fee;
    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表
     */
    private String refund_fee_type;
    /**
     * 若商户传入，会在下发给用户的退款消息中体现退款原因
     */
    private String refund_desc;
    /**
     * 仅针对老资金流商户使用
     * REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
     * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     */
    private String refund_account;

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

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_fee_type() {
        return refund_fee_type;
    }

    public void setRefund_fee_type(String refund_fee_type) {
        this.refund_fee_type = refund_fee_type;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }
}
