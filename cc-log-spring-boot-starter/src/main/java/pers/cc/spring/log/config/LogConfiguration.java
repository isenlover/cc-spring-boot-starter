package pers.cc.spring.log.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.log.elk.properties.ELKLogProperties;

/**
 * @author chengce
 * @version 2021-03-24 23:26
 */
@ComponentScan("pers.cc.spring.log")
@EnableConfigurationProperties({ELKLogProperties.class,})
public class LogConfiguration {
}
