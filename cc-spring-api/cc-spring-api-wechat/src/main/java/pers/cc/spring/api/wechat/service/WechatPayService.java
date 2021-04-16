package pers.cc.spring.api.wechat.service;

import pers.cc.spring.api.wechat.model.pay.dto.WxRefundDTO;
import pers.cc.spring.api.wechat.model.pay.dto.WxUnifiedOrderDTO;
import pers.cc.spring.api.wechat.model.pay.param.WxJsPayParameter;
import pers.cc.spring.api.wechat.model.pay.response.WxPayMessage;
import pers.cc.spring.api.wechat.model.pay.response.WxRefundMessage;
import pers.cc.spring.core.message.Message;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * com.cc.api.wechat.service
 *
 * @author chengce
 * @version 2017-10-25 13:45
 */
public interface WechatPayService {

  /**
   * 生成js前端支付所需参数
   *
   * @param wxUnifiedOrderDTO 签名字段接口计算，无须填写
   * @return js前端所需字段
   */
  Message<WxJsPayParameter> JsPay(WxUnifiedOrderDTO wxUnifiedOrderDTO);

  Message<WxPayMessage> pay(WxUnifiedOrderDTO wxUnifiedOrderDTO);

  /**
   * 微信支付退款
   * 没有回调，支付返回退款结果
   *
   * @param wxRefundDTO 退款所需参数
   * @return 退款结果信息
   */
  Message<WxRefundMessage> refund(WxRefundDTO wxRefundDTO) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException;
}
