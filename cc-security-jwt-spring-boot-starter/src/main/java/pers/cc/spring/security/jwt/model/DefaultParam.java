package pers.cc.spring.security.jwt.model;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author chengce
 * @version 2021-03-23 14:43
 */
public class DefaultParam {

  public static List<String> DEFAULT_PERMIT_REQUEST = asList(
      "/socket/**"
      , "/csrf"
      , "/**/login/**"
      , "login"
      , "register"
  );

  public static List<String> DEFAULT_SWAGGER_PERMIT_REQUEST = asList(
      "/swagger-resources/**"
      , "/v2/api-docs/**"
      , "/webjars/springfox-swagger-ui/**"
  );
}
