package pers.cc.spring.data.redis.annotation.cache;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-05-29 22:28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ParamName {
  String value();
}
