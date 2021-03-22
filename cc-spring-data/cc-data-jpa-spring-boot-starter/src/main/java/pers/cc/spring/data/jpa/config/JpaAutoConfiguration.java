package pers.cc.spring.data.jpa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static pers.cc.spring.core.scanner.Scan.PACKAGE_CC_SPRING_DATA_JPA;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
//@Configuration
@ComponentScan(PACKAGE_CC_SPRING_DATA_JPA)
// 暴露代理对象
@EnableJpaRepositories
@EnableAspectJAutoProxy(exposeProxy = true)
public class JpaAutoConfiguration {
}
