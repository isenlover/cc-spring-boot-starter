package pers.cc.spring.data.redis.annotation.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存机制：
 * 每次会执行函数并且重新缓存
 *
 * @author chengce
 * @version 2018-05-29 23:45
 * @see RedisCachePut
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCachePut {
  /**
   * 默认为函数名称
   * 支持SpEL条件语句
   */
  String key() default "";

  /**
   * 默认存储1分钟
   */
  long time() default 10;

  /**
   * 默认单位 分钟
   */
  TimeUnit timeUnit() default TimeUnit.MINUTES;

  /**
   * 是否永久保存
   * 如果为true
   * 时间设置将失效
   */
  boolean forever() default true;

  /**
   * 是否存储为null的元素
   * 是否存储字符串为""的元素
   * 是否存储列表size为0的元素
   */
  boolean saveBlank() default false;

  /**
   * SpEL条件语句，为true才存储
   */
  String condition() default "";
}
