package pers.cc.spring.security.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.security.jwt.properties.JwtProperties;
import pers.cc.spring.security.jwt.service.JwtService;

/**
 * @author chengce
 * @version 2018-06-23 15:05
 */
@Configuration
@ConditionalOnClass(JwtService.class)
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfiguration {
    @Autowired
    JwtProperties jwtProperties;
}
