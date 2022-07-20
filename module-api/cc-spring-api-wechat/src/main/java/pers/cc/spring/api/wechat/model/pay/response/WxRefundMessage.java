package pers.cc.spring.api.wechat.model.pay.response;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay
 * 微信退款回调
 *
 * @author chengce
 * @version 2017-10-25 14:57
 */
public class WxRefundMessage extends WxPayBaseMessage {
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
     * 微信订单号
     */
    private String transaction_id;
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

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String out_trade_no;

    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    private String out_refund_no;

    /**
     * 微信退款单号
     */
    private String refund_id;
    /**
     * 退款总金额,单位为分,可以做部分退款
     */
    private String refund_fee;
    /**
     * 去掉非充值代金券退款金额后的退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
     */
    private String settlement_refund_fee;
    /**
     * 订单总金额，单位为分，只能为整数
     */
    private String total_fee;
    /**
     * 去掉非充值代金券金额后的订单总金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额
     */
    private String settlement_total_fee;
    /**
     * 现金支付金额，单位为分，只能为整数
     */
    private String cash_fee;
    /**
     * 现金退款金额，单位为分，只能为整数
     */
    private String cash_refund_fee;
    /**
     * 订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String fee_type;
    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String cash_fee_type;

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

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
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

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getSettlement_refund_fee() {
        return settlement_refund_fee;
    }

    public void setSettlement_refund_fee(String settlement_refund_fee) {
        this.settlement_refund_fee = settlement_refund_fee;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(String settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_refund_fee() {
        return cash_refund_fee;
    }

    public void setCash_refund_fee(String cash_refund_fee) {
        this.cash_refund_fee = cash_refund_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }
}
