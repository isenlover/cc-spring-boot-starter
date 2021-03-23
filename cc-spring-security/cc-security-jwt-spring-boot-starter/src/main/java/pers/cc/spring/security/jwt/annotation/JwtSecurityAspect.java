package pers.cc.spring.security.jwt.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.security.jwt.model.DefaultParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengce
 * @version 2021-03-23 14:01
 */
@Slf4j
@Aspect
@Configuration
public class JwtSecurityAspect {

  @Before(value = "execution(* *(..)) && @annotation(jwtSecurityParam)")
  public void before(JwtSecurityParam jwtSecurityParam) {
    List<String> permits = new ArrayList<>();
    if (jwtSecurityParam.permitDefault()) {
      permits.addAll(DefaultParam.DEFAULT_PERMIT_REQUEST);
      permits.addAll(Arrays.stream(jwtSecurityParam.value()).collect(Collectors.toList()));
    }
    if (jwtSecurityParam.permitSwagger()) {
      permits.addAll(DefaultParam.DEFAULT_SWAGGER_PERMIT_REQUEST);
    }
    permitRequestList(permits);
  }

//  @Bean
//  @ConditionalOnBean(annotation = JwtSecurityParam.class)
  List<String> permitRequestList(List<String> permits) {
    return permits;
  }
}
