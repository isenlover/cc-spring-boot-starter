package pers.cc.spring.api.ifeige.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import pers.cc.spring.api.ifeige.properties.IFeiGeApiProperties;

import static pers.cc.spring.core.scanner.Scan.PACKAGE_CC_SPRING_API_IFEIGE;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@EnableConfigurationProperties(
    {
        IFeiGeApiProperties.class,
    }
)
@ComponentScan(PACKAGE_CC_SPRING_API_IFEIGE)
// 暴露代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class IFeiGeAutoConfiguration {
}
