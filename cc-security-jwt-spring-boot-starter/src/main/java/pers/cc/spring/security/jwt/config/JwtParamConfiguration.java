package pers.cc.spring.security.jwt.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.security.jwt.annotation.JwtSecurityParam;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;

import java.util.Arrays;
import java.util.Map;

/**
 * @author chengce
 * @version 2021-03-24 17:25
 */
public class JwtParamConfiguration {

  @Bean
  JwtSecurityParamBean jwtSecurityParamBean() {
    Map<String, Object> paramMap = JSON.parseObject(System.getProperty("JwtSecurityParam"));
    JwtSecurityParamBean jwtSecurityParamBean = ClassUtils.mapToObject(paramMap, JwtSecurityParamBean.class);
    String permitRequestsText = (String) paramMap.get("permitRequestsText");
    String allowOriginText = (String) paramMap.get("allowOriginText");
    jwtSecurityParamBean.setPermitRequests(permitRequestsText.split(";"));
    jwtSecurityParamBean.setAllowOrigins(Arrays.asList(allowOriginText.split(";")));
    return jwtSecurityParamBean;
  }
}
