package pers.cc.spring.api.wechat.annotation;

import java.lang.annotation.*;

/**
 * 注解写在任意地方
 *
 * @author chengce
 * @version 2018-06-01 14:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WechatApp {

  /**
   * AppId
   */
  String appId();

  /**
   * AppSecret
   */
  String appSecret();

  /**
   * 商户Id
   */
  String merchantId() default "";

  /**
   * 商户Key
   */
  String merchantKey() default "";

  /**
   * 微信自定义token
   */
  String token() default "ccToken";

  /**
   * 微信公众号微信号
   */
  String id() default "";
}
