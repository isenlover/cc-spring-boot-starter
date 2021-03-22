package pers.cc.spring.api.aliyun.sms.annotation;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-14 22:28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AliyunSMSApp {

  String sign();

  String template();

  String param();

  /**
   * 短信过期时间，秒，缓存时间
   */
  int expiration() default 300;

  /**
   * 两次短信间隔最短时间，秒
   */
  int timeout() default 60;
}
