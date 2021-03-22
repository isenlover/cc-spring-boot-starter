package pers.cc.spring.security.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2018-06-23 11:54
 */
@ConfigurationProperties(
    prefix = "spring.security.jwt"
)
@Data
public class JwtProperties {

  /**
   * httpHeader
   */
  private String httpHeader = "Authorization";

  /**
   * token有效期，默认30天，单位秒
   */
  private int expiration = 2592000;
}
