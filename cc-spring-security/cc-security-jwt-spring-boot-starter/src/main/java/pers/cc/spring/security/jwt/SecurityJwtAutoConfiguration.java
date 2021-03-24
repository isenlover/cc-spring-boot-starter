package pers.cc.spring.security.jwt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.security.jwt.annotation.JwtSecurityParam;
import pers.cc.spring.security.jwt.config.JwtSecurityConfigurationImport;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;
import pers.cc.spring.security.jwt.properties.JwtProperties;
import pers.cc.spring.security.jwt.service.impl.JwtServiceImpl;

import java.util.Map;

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
