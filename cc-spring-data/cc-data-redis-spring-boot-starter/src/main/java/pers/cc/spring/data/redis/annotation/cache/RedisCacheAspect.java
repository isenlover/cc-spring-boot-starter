package pers.cc.spring.data.redis.annotation.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.data.redis.annotation.cache.util.RedisCacheService;
import pers.cc.spring.data.redis.service.RedisService;

import java.util.Optional;

import static pers.cc.spring.data.redis.service.RedisService.CACHE_OPTIONAL;

/**
 * 缓存注解
 *
 * @author chengce
 * @version 2018-05-24 17:36
 * @see RedisCache
 */
@Slf4j
@Aspect
@Component
public class RedisCacheAspect {

  @Autowired
  RedisCacheService redisCacheService;

  @Autowired
  RedisService redisService;

  @Around(value = "execution(* *(..)) && @annotation(redisCache)")
  public Object redisCache(ProceedingJoinPoint proceedingJoinPoint, RedisCache redisCache) throws Throwable {
    String cacheKey = CommonUtils.getCacheKey(redisCache.key(), proceedingJoinPoint);
    if (redisService.exist(cacheKey)) {
      return redisService.getValue(cacheKey);
    } else {
      // 判断是否满足存储条件
      Object result = proceedingJoinPoint.proceed();
      if (redisCacheService.canCache(proceedingJoinPoint, result, redisCache.saveBlank(), redisCache.condition(),
          redisCache.key())) {
        redisCacheService.cache(cacheKey, result, redisCache.forever(), redisCache.time(),
            redisCache.timeUnit());
      }
      return result;
    }
  }
}
