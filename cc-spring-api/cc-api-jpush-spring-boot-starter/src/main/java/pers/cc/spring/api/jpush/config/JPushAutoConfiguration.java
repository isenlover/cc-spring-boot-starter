package pers.cc.spring.api.jpush.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pers.cc.spring.api.jpush.properties.JPushApiProperties;


/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@EnableConfigurationProperties(
    {
        JPushApiProperties.class,
    }
)
// 暴露代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class JPushAutoConfiguration {
}
