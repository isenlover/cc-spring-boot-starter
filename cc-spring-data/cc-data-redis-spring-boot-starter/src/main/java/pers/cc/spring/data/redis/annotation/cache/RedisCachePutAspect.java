package pers.cc.spring.data.redis.annotation.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.data.redis.annotation.cache.util.RedisCacheService;
import pers.cc.spring.data.redis.service.RedisService;

/**
 * @author chengce
 * @version 2018-05-30 00:08
 */
@Aspect
@Component
public class RedisCachePutAspect {

    @Autowired
    RedisService redisService;

    @Autowired
    RedisCacheService redisCacheService;

    @AfterReturning(value = "execution(* *(..)) && @annotation(redisCachePut)", returning = "result")
    public void updateRedisCache(JoinPoint joinPoint, RedisCachePut redisCachePut, Object result) {
        String cacheKey = CommonUtils.getCacheKey(redisCachePut.key(), joinPoint);
        redisService.remove(cacheKey);
        if (redisCacheService.canCache(joinPoint, result, redisCachePut.saveBlank(), redisCachePut.condition(),
                redisCachePut.key())) {
            redisCacheService.cache(cacheKey, result, redisCachePut.forever(), redisCachePut.time(),
                    redisCachePut.timeUnit());
        }
    }
}
