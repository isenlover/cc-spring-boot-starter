package pers.cc.spring.api.jpush.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import pers.cc.spring.api.jpush.properties.JPushApiProperties;
import pers.cc.spring.api.jpush.service.impl.JPushServiceImpl;


/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Configuration
@EnableConfigurationProperties({JPushApiProperties.class})
@EnableAspectJAutoProxy(exposeProxy = true)
@Import(JPushServiceImpl.class)
public class JPushAutoConfiguration {
}
