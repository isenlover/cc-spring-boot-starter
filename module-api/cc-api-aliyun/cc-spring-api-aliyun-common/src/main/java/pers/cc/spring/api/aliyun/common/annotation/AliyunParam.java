package pers.cc.spring.api.aliyun.common.annotation;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2021-03-23 18:12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AliyunParam {
  String key();

  String secret();
}
