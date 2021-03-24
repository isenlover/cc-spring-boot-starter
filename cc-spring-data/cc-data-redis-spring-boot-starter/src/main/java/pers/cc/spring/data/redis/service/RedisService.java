package pers.cc.spring.data.redis.service;

import com.querydsl.core.QueryResults;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis通用接口
 *
 * @author chengce
 * @version 2017-12-12 22:35
 */
public interface RedisService {

  String CACHE_OPTIONAL = "OPTIONAL";

  /**
   * 缓存一个值【不会存储空值】
   *
   * @param key   键
   * @param value 值
   * @param time  缓存时间，单位s
   */
  void cacheValue(String key, Object value, long time);

  /**
   * 缓存一个值【不会存储空值】
   *
   * @param key      键
   * @param value    值
   * @param time     时间
   * @param timeUnit 时间单位
   */
  void cacheValue(String key, Object value, long time, TimeUnit timeUnit);

  /**
   * 缓存一个值
   *
   * @param key         键
   * @param value       值
   * @param isSaveBlank 是否存储空值
   * @param time        时间
   * @param timeUnit    时间单位
   */
  void cacheValue(String key, Object value, boolean isSaveBlank, long time, TimeUnit timeUnit);

  /**
   * 获取缓存里的值
   *
   * @param key 键
   * @return 值
   */
  <E> E getValue(String key);

  /**
   * 获取 jpa的queryResult
   *
   * @param key 键
   * @return 查询结果
   */
  <T> QueryResults<T> getQueryResult(String key);

  /**
   * 获取 Optional
   *
   * @param key 键
   * @return 查询结果
   */
  <T> Optional<T> getOption(String key);

  /**
   * 检查是否有此缓存
   *
   * @param key 键
   * @return 是否包含
   */
  boolean exist(String key);

  /**
   * 删除缓存
   *
   * @param key 键
   */
  void remove(String key);

  /**
   * 删除key 例如key的模糊搜索，会删除 *key*的所有缓存
   *
   * @param key 键
   */
  void removeKeys(String key);

  /**
   * 删除缓存
   *
   * @param keys 键
   */
  void remove(List<String> keys);

  /**
   * 删除key 例如key的模糊搜索，会删除 *key*的所有缓存
   *
   * @param keys 键
   */
  void removeKeys(List<String> keys);
}
