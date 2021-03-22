package pers.cc.spring.data.redis.annotation.cache;

/**
 * @author chengce
 * @version 2018-05-24 18:34
 */
public enum RedisCacheEvictMethod {
    /**
     * 每次会执行函数并且删除缓存
     */
    DELETE,
    /**
     * 每次会执行函数并且删除所有跟key相关缓存
     */
    DELETE_ALL,
    ;

    private RedisCacheEvictMethod() {
    }
}
