package pers.cc.spring.log.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.log.elk.properties.ELKLogProperties;

import static pers.cc.spring.core.scanner.Scan.PACKAGE_CC_SPRING_LOG;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@EnableConfigurationProperties(
        {
                ELKLogProperties.class,
        }
)
@ComponentScan(PACKAGE_CC_SPRING_LOG)
public class LogAutoConfiguration {
}
