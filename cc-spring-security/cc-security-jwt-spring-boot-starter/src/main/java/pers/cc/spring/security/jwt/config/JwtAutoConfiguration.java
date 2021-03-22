package pers.cc.spring.security.jwt.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pers.cc.spring.security.jwt.properties.JwtProperties;

import static pers.cc.spring.core.scanner.Scan.PACKAGE_CC_SPRING_SECURITY_JWT;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@ComponentScan(PACKAGE_CC_SPRING_SECURITY_JWT)
@EnableConfigurationProperties(
    {
        JwtProperties.class
    }
)
// 暴露代理对象
@EnableAspectJAutoProxy(exposeProxy = true)
public class JwtAutoConfiguration {
}
