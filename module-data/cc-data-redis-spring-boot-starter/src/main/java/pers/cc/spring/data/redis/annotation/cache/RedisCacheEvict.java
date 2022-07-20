package pers.cc.spring.data.redis.annotation.cache;

import java.lang.annotation.*;

/**
 * 缓存清理
 * 清理机制有两种 {@link RedisCacheEvictMethod}
 *
 * @author chengce
 * @version 2018-05-29 23:43
 * @see RedisCacheEvictAspect
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
// TODO: 2018/6/15 加@repeatable
public @interface RedisCacheEvict {
  /**
   * 支持SpEL条件语句
   */
  String[] key();

  /**
   * 缓存清理方式
   * 默认RedisCacheType.DELETE
   *
   * @see RedisCacheEvictMethod#DELETE
   */
  RedisCacheEvictMethod cacheMethod() default RedisCacheEvictMethod.DELETE;
}
