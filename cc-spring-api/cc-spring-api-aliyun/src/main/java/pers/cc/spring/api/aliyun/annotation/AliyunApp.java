package pers.cc.spring.api.aliyun.annotation;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-14 22:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AliyunApp {
  String key();

  String secret();
}
