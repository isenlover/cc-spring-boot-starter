package pers.cc.spring.api.aliyun.alipay.annotation;

import java.lang.annotation.*;

/**
 * 如果不涉及提现/红包功能，只需要 appPrivateKey  alipayPublicKey 这两个值
 * 否则需要公钥证书，一共3个
 * @author chengce
 * @version 2021-03-23 18:12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AliPayApp {

  /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
  String appId();

  /** 应用私钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602469554 **/
  String appPrivateKey();

  /** 支付宝公钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602487431 **/
  String alipayPublicKey();
}
