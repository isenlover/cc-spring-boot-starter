package pers.cc.spring.api.aliyun.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import pers.cc.spring.api.aliyun.alipay.bean.PayModel;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * @author chengce
 * @version 2021-04-20 12:39
 */
public interface AlipayService {

  /**
   * @param payModel 支付参数
   * @return 整个支付页面
   */
  String pay(PayModel payModel);

  String payByCert(PayModel payModel) throws FileNotFoundException;

  Optional<AlipayFundTransUniTransferResponse> transferByCert(String account, String name, String amount, String orderId, String title, String remark) throws AlipayApiException, FileNotFoundException;

  /**
   * 回调验签
   * @param httpServletRequest
   * @return
   */
  boolean checkSignByKey(HttpServletRequest httpServletRequest);

  boolean checkCertSignByKey(HttpServletRequest httpServletRequest);
}
