package pers.cc.spring.security.jwt;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import pers.cc.spring.security.jwt.config.JwtSecurityConfigurationImport;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@Import(JwtSecurityConfigurationImport.class)
public class SecurityJwtAutoConfiguration {

}
