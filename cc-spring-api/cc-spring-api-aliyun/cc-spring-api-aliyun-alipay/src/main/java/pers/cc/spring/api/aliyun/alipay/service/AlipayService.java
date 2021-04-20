package pers.cc.spring.api.aliyun.alipay.service;

import pers.cc.spring.api.aliyun.alipay.bean.PayModel;

import javax.servlet.http.HttpServletRequest;

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

  /**
   * 回调验签
   * @param httpServletRequest
   * @return
   */
  boolean checkSignByKey(HttpServletRequest httpServletRequest);
}
