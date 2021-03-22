package pers.cc.spring.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2018-07-12 19:00
 */
@Data
@ConfigurationProperties(
    prefix = "api"
)
public class CoreApiProperties {
  private String version;

  /**
   * requestMapping version
   */
  private String mappingVersion;
}