package pers.cc.spring.data.redis.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import pers.cc.spring.core.message.MessageCode;

import java.lang.annotation.*;

/**
 * 限制controller访问次数
 *
 * @author chengce
 * @version 2017-07-04 12:40
 * @see RequestLimitAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
  /**
   * 允许访问的次数
   */
  int value() default 30;

  /**
   * 时间段，多少时间段内运行访问count次
   * 单位秒
   */
  long time() default 60;

  /**
   * 超出限制后的提示语句
   * 默认
   *
   * @see MessageCode#BAD_REQUEST_REQUEST_LIMIT
   */
  String message() default "";

}
