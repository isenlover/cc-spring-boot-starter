package pers.cc.spring.data.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.querydsl.core.QueryResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.data.redis.annotation.cache.RedisOptional;
import pers.cc.spring.data.redis.exception.RedisRuntimeException;
import pers.cc.spring.data.redis.service.RedisService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 缓存接口工具
 * 提供字符串key，值Object任意子类的操作
 *
 * @author chengce
 * @version 2017-12-12 22:39
 * @see RedisRuntimeException 缓存异常
 * @see pers.cc.spring.core.exception.BaseRuntimeException
 */
@Service
public class RedisServiceImpl implements RedisService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public void cacheValue(String key, Object value, long time) {
    cacheValue(key, value, time, TimeUnit.SECONDS);
  }

  @Override
  public void cacheValue(String key, Object value, long time, TimeUnit timeUnit) {
    cacheValue(key, value, false, time, timeUnit);
  }

  @Override
  public void cacheValue(String key, Object value, boolean isSaveBlank, long time, TimeUnit timeUnit) {
    if (time <= 0) {
      throw new RedisRuntimeException(key + "缓存时间必须大于0");
    }
    if (!isSaveBlank && (CommonUtils.isEmpty(key) || CommonUtils.isEmpty(value))) {
      return;
    }
    try {
      ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
//            if (value instanceof QueryResults) {
//                value = JSON.toJSONString(value);
//            }
      valueOps.set(key, value);
      redisTemplate.expire(key, time, timeUnit);
    } catch (Throwable t) {
      throw new RedisRuntimeException(key + "缓存失败");
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <E> E getValue(String key) {
    try {
      ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
      E value = (E) valueOps.get(key);
      if (value instanceof RedisOptional) {
        return (E) Optional.ofNullable(((RedisOptional) value).getData());
      }
      return value;
    } catch (Throwable t) {
      throw new RedisRuntimeException(key + "获取缓存失败");
    }
  }

  @Override
  public <T> QueryResults<T> getQueryResult(String key) {
    return JSON.parseObject(getValue(key), QueryResults.class);
  }

  @Override
  public boolean exist(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Throwable t) {
      throw new RedisRuntimeException(t.getLocalizedMessage());
    }
  }

  @Override
  public void remove(String key) {
    try {
      redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*" + key + "*")));
    } catch (Throwable t) {
      throw new RedisRuntimeException(key + "缓存删除失败");
    }
  }

  @Override
  public void removeKeys(String key) {
    redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*" + key + "*")));
  }

  @Override
  public void remove(List<String> keys) {
    keys.forEach(this::remove);
  }

  @Override
  public void removeKeys(List<String> keys) {
    keys.forEach(this::removeKeys);
  }
}
