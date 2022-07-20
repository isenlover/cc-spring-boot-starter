package pers.cc.spring.data.redis.annotation.cache;

import lombok.Data;

/**
 * 解决redis不能缓存optional问题
 *
 * @author chengce
 * @version 2018-11-28 17:48
 */
@Data
public class RedisOptional<T> {

  private T data;

  private RedisOptional() {

  }

  public static <T> RedisOptional of(T data) {
    RedisOptional redisOptional = new RedisOptional();
    redisOptional.data = data;
    return redisOptional;
  }
}
