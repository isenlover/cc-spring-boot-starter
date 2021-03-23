package pers.cc.spring.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * @author chengce
 * @version 2021-03-23 14:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConditionalOnBean(annotation = pers.cc.spring.security.jwt.annotation.JwtSecurityParam.class)
public class JwtSecurityParamBean {

  private String[] permitRequests;

  private String tokenHead;

  /**
   * httpHeader
   */
  private String httpHeader = "Authorization";

  /**
   * token有效期，默认30天，单位秒
   */
  private int expiryTime = 86400;
}
