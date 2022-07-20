package pers.cc.spring.data.redis.annotation.cache.util;

import org.aspectj.lang.JoinPoint;

import java.util.concurrent.TimeUnit;

/**
 * @author chengce
 * @version 2018-05-30 00:23
 */
public interface RedisCacheService {

  void cache(String key, Object result, boolean forever, long time, TimeUnit timeUnit);

  boolean canCache(JoinPoint joinPoint, Object result, boolean saveBlank, String condition);

}
