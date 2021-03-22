package pers.cc.spring.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pers.cc.spring.core.properties.CoreApiProperties;
import pers.cc.spring.core.properties.CoreProperties;

import static pers.cc.spring.core.scanner.Scan.PACKAGE_CC_SPRING_CORE;

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
@ComponentScan(PACKAGE_CC_SPRING_CORE)
// 暴露代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class CoreAutoConfiguration {
}
