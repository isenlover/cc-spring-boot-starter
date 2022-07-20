package pers.cc.spring.data.redis.annotation.cache;

import org.aspectj.lang.JoinPoint;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.page.PageResults;
import pers.cc.spring.data.redis.annotation.cache.util.RedisCacheService;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存注解
 * 如果结果是Message类，则会判断其data
 * 如果为PageResults类，则会判断是否有值
 * 缓存机制：
 * 每次会先判断是否有缓存
 * 如果指定缓存存在则返回，不会执行函数
 * 如果指定缓存不存在则执行函数，并最终保存
 *
 * @author chengce
 * @version 2018-05-24 17:33
 * @see RedisCacheAspect
 * @see RedisCacheService
 * @see Message
 * @see PageResults
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RedisCache {
  /**
   * 默认为格式为：simple类名#方法名称(参数值1,参数值2)
   * 如果参数为Object 取 hashCode
   * key 为空时{@link pers.cc.spring.core.util.CommonUtils#getCacheKeys(String[], JoinPoint)}
   * <p>
   * <p>
   * 支持SpEL条件语句
   */
  String key() default "";

  /**
   * 默认存储30分钟
   */
  long time() default 30;

  /**
   * 默认单位 小时
   */
  TimeUnit timeUnit() default TimeUnit.MINUTES;

  /**
   * 是否永久保存
   * 如果为true
   * 时间设置将失效
   */
  boolean forever() default false;

  /**
   * 是否存储为null的元素
   * 是否存储字符串为""的元素
   * 是否存储列表size为0的元素
   */
  boolean saveBlank() default false;

  /**
   * SpEL条件语句，为true才存储
   * 可以多条件
   */
  String condition() default "";

}
