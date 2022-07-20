package pers.cc.spring.api.nim.annotation;

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
public @interface NimApp {

  /**
   * AppId
   */
  String appKey();

  /**
   * AppSecret
   */
  String appSecret();
}
