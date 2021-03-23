package pers.cc.spring.security.jwt.config;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.security.jwt.annotation.JwtSecurityParam;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;
import pers.cc.spring.security.jwt.properties.JwtProperties;

import java.util.Map;

import static pers.cc.spring.security.jwt.model.DefaultParam.DEFAULT_PERMIT_REQUEST;
import static pers.cc.spring.security.jwt.model.DefaultParam.DEFAULT_SWAGGER_PERMIT_REQUEST;

/**
 * @author chengce
 * @version 2018-06-23 15:05
 */
@Configuration
@ConditionalOnClass({JwtSecurityParamBean.class, UserDetailsService.class})
@EnableConfigurationProperties({JwtProperties.class})
@ComponentScan("pers.cc.spring.security.jwt")
public class JwtConfiguration {
  @Autowired
  JwtProperties jwtProperties;

  @Bean
  @ConditionalOnMissingBean(JwtSecurityParamBean.class)
  JwtSecurityParamBean jwtSecurityParamBean() {
    return JwtSecurityParamBean.builder()
        .permitRequests(Lists.newArrayList(Iterables.concat(DEFAULT_PERMIT_REQUEST, DEFAULT_SWAGGER_PERMIT_REQUEST)).toArray(new String[]{}))
        .tokenHead("ccToken#")
        .expiryTime(2592000)
        .httpHeader("Authorization")
        .build();
  }

  @Bean
  @Primary
  @ConditionalOnBean(annotation = JwtSecurityParam.class)
  JwtSecurityParamBean jwtSecurityParamBeanCustom() {
    Map<String, Object> paramMap = JSON.parseObject(System.getProperty("JwtSecurityParam"));
    JwtSecurityParamBean jwtSecurityParamBean = ClassUtils.mapToObject(paramMap, JwtSecurityParamBean.class);
    String permitRequestsText = (String) paramMap.get("permitRequestsText");
    jwtSecurityParamBean.setPermitRequests(permitRequestsText.split(";"));
    return jwtSecurityParamBean;
  }
}
