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

}
