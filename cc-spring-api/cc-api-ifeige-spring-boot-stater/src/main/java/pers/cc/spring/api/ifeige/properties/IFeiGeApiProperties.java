package pers.cc.spring.api.ifeige.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2018-07-12 19:00
 * @link {https://u.ifeige.cn/wiki}
 */
@Data
@ConfigurationProperties(
    prefix = "ifeige"
)
public class IFeiGeApiProperties {
  private String secret;

  private String appKey;

  private String templateId;
}