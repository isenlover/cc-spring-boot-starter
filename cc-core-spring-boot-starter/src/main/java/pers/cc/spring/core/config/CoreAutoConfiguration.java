package pers.cc.spring.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pers.cc.spring.core.properties.CoreApiProperties;
import pers.cc.spring.core.properties.CoreProperties;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@EnableConfigurationProperties(
    {
        CoreProperties.class,
        CoreApiProperties.class
    }
)
@ComponentScan("pers.cc.spring.core")
// 暴露代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class CoreAutoConfiguration {
}
