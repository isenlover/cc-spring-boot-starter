package pers.cc.spring.api.wechat.model.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.cc.spring.api.wechat.enums.WechatPayTradeType;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay
 * 微信统一下单
 *
 * @author chengce
 * @version 2017-10-25 13:47
 * @link https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxUnifiedOrderDTO {
  /**
   * required
   * 微信开放平台审核通过的应用APPID 或公众号appid
   */
  private String appid;

  /**
   * required
   * 微信支付分配的商户号
   */
  private String mch_id;

  /**
   * optional
   * 终端设备号(门店号或收银设备ID)，默认请传"WEB"
   */
  private String device_info;

  /**
   * required
   * 随机字符串，不长于32位
   *
   * @link https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
   */
  private String nonce_str;

  /**
   * required
   * 签名
   */
  private String sign;

  /**
   * optional
   * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
   */
  private String sign_type;

  /**
   * required
   * 商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
   */
  private String body;

  /**
   * optional
   * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
   *
   * @link https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
   */
  private String detail;

  /**
   * optional
   * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
   */
  private String attach;

  /**
   * required
   * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
   *
   * @link https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2
   */
  private String out_trade_no;

  /**
   * optional
   * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
   */
  private String fee_type;

  /**
   * required
   * 订单总金额，单位为分
   */
  private String total_fee;

  /**
   * required
   * 用户端实际ip
   */
  private String spbill_create_ip;

  /**
   * optional
   * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
   */
  private String time_start;

  /**
   * optional
   * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
   * 注意：最短失效时间间隔必须大于5分钟
   */
  private String time_expire;

  /**
   * optional
   * 订单优惠标记，代金券或立减优惠功能的参数
   */
  private String goods_tag;

  /**
   * required
   * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
   */
  private String notify_url;

  /**
   * required
   * 支付类型
   * JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
   */
  private WechatPayTradeType trade_type;

  /**
   * optional
   * no_credit--指定不能使用信用卡支付
   */
  private String limit_pay;

  /**
   * optional
   * 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
   * {
   * "store_id": "", //门店唯一标识，选填，String(32)
   * "store_name":"”//门店名称，选填，String(64)
   * }
   */
  private String scene_info;

  /**
   * 公众号支付需要提供openId
   */
  private String openid;
}
