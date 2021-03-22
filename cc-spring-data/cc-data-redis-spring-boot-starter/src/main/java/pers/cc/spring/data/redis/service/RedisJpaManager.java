package pers.cc.spring.data.redis.service;


import pers.cc.spring.data.redis.config.RedisConfiguration;

import java.lang.reflect.Method;

/**
 * 基于jpa的redis管理接口
 *
 * @author chengce
 * @version 2018-01-20 19:28
 */
public interface RedisJpaManager {

    /**
     * 获取缓存key
     * 根据{@link RedisConfiguration#jpaKeyGenerator()}规则
     * 获取当前jpa Repository的缓存键
     *
     * @param method 方法名 {@link Method#getName()}
     * @param params 参数
     * @return 缓存键key
     * @see RedisConfiguration#jpaKeyGenerator()
     */
    String getCacheKey(String method, Object... params);

    /**
     * 获取模糊的缓存键，适用于清除翻页等模糊缓存
     * 相当于 {@link this#getCacheKey(String, Object...)} 清除了末尾的)符号
     *
     * @param method 方法名 {@link Method#getName()}
     * @param params 参数
     * @return 模糊缓存键
     * @see this#getCacheKey(String, Object...)
     */
    String getFuzzyCacheKey(String method, Object... params);
}
