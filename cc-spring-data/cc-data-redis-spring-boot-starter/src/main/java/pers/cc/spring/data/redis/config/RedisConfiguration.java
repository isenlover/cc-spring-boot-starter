package pers.cc.spring.data.redis.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.StringUtils;

/**
 * com.cc.redis.config
 * redis缓存配置
 *
 * @author chengce
 * @version 2017-10-11 11:51
 */
// 2021.1.22 这里不用自带缓存
//@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {
//    @Value("${spring.redis.host:redis}")
//    private String host;
//
//    @Value("${spring.redis.port:6379}")
//    private int port;
//
//    @Value("${spring.redis.timeout:60}")
//    private int timeout;

//    @Value("${spring.redis.expired:2592000}")
//    private int expired;

  /**
   * 针对 jpaRepository的redis缓存策略
   * 所有参数为key值
   * 当参数为空时，存储格式：类名.方法名
   * 当有参数时，存储格式：类名.方法名(param,param...)
   */
  @Bean
  public KeyGenerator jpaKeyGenerator() {
    return (target, method, params) -> {
      StringBuilder sb = new StringBuilder();
      String baseKey = method.getDeclaringClass().getSimpleName() + "." + method.getName();
      if (CommonUtils.isEmpty(params) || params.length == 0) {
        return baseKey;
      }
      for (Object param : params) {
        sb.append(param.toString()).append(",");
      }
      baseKey += "(" + StringUtils.removeLastCharacter(sb.toString()) + ")";
      return baseKey;
    };
  }

  /**
   * since 2.0 官方取消了redisTemplate创建方式
   *
   * @param connectionFactory RedisConnectionFactory
   * @return CacheManager
   * @see <a href="https://docs.spring.io/spring-data/redis/docs/2.0.5.RELEASE/reference/html/#new-in-2.0.0"></a>
   * @see <a href="https://my.oschina.net/mrfu/blog/1631805"></a>
   */
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
        .build();
    // 单位秒
//        cacheManager.setDefaultExpiration(expired);
    return cacheManager;
  }

  /**
   * 这里主要是要改变默认的序列化
   * 实际测试后，不改变序列方式的情况下，key默认是值+KEY组合
   * FIXED 2021.1.22
   *
   * @param factory RedisConnectionFactory
   */
  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplateApp(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    RedisSerializer<String> keySerializer = new StringRedisSerializer();
    // 设置key序列化类，否则key前面会多了一些乱码，其实是value+key
    redisTemplate.setKeySerializer(keySerializer);
    redisTemplate.setHashKeySerializer(keySerializer);
    // json序列化工具
    GenericFastJsonRedisSerializer valueSerializer = new GenericFastJsonRedisSerializer();
    // 设置内容序列化类
    redisTemplate.setValueSerializer(valueSerializer);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
