package pers.cc.spring.data.redis.config;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Import(RedisConfigurationImport.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class RedisAutoConfiguration {
}