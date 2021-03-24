package pers.cc.spring.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import pers.cc.spring.security.jwt.config.JwtSecurityConfigurationImport;
import pers.cc.spring.security.jwt.properties.JwtProperties;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@Slf4j
@EnableConfigurationProperties({JwtProperties.class})
//@EnableAspectJAutoProxy(exposeProxy = true)
@Import(JwtSecurityConfigurationImport.class)
//@ComponentScan("pers.cc.spring.security.jwt")
public class SecurityJwtAutoConfiguration {

}
