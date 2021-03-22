package pers.cc.spring.data.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import static pers.cc.spring.core.scanner.Scan.PACKAGE_CC_SPRING_DATA_REDIS;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
//@Configuration 2021.1.22 使用factories自动注解
@ComponentScan(PACKAGE_CC_SPRING_DATA_REDIS)
// 暴露代理对象
@Import(RedisConfigurationImport.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class RedisAutoConfiguration {
}