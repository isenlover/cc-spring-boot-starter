package pers.cc.spring.amqp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import pers.cc.spring.amqp.properties.AmqpProperties;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@EnableConfigurationProperties(
    {
        AmqpProperties.class,
    }
)
@ComponentScan("pers.cc.spring.amqp")
// 暴露代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class AmqpAutoConfiguration {
}
