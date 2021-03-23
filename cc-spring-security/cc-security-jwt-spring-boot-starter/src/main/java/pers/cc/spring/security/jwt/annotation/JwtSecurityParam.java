package pers.cc.spring.security.jwt.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.security.jwt.config.JwtSecurityParamImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2021-03-23 14:01
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JwtSecurityParamImport.class)
public @interface JwtSecurityParam {

  /**
   * 参考 HttpSecurity antMatchers
   * @return
   */
  String[] value();

  /**
   * 是否还保留系统自带的Permit Request
   * true = 保留，并在后面添加
   */
  boolean permitDefault() default true;

  /**
   * swagger需要的html等等资源权限
   * true = 赋予swagger资源权限
   */
  boolean permitSwagger() default true;

  String tokenHead() default "ccToken#";

  String httpHeader() default "Authorization";

  /**
   * Token有效期，秒，默认30天
   */
  int expiryTime() default 86400;
}
