package pers.cc.spring.data.redis.annotation.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.data.redis.annotation.cache.util.RedisCacheService;
import pers.cc.spring.data.redis.service.RedisService;

import java.util.List;

/**
 * @author chengce
 * @version 2018-05-30 00:02
 * @see RedisCacheEvict
 */
@Aspect
@Component
public class RedisCacheEvictAspect {

    @Autowired
    RedisCacheService redisCacheService;

    @Autowired
    RedisService redisService;


    @After(value = "execution(* *(..)) && @annotation(redisCacheEvict)")
    public void removeRedisCache(JoinPoint joinPoint, RedisCacheEvict redisCacheEvict) {
        List<String> keys;
        if (CommonUtils.isNotEmpty(redisCacheEvict.key())) {
            keys = CommonUtils.getCacheKeys(redisCacheEvict.key(), joinPoint);
            switch (redisCacheEvict.cacheMethod()) {
                case DELETE:
                    redisService.remove(keys);
                    break;
                default:
                    redisService.removeKeys(keys);
                    break;
            }
        }
    }
}
