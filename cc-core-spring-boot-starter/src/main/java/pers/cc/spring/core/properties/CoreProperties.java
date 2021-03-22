package pers.cc.spring.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author chengce
 * @version 2018-07-12 11:57
 */
@Data
@ConfigurationProperties(
    prefix = "core"
)
public class CoreProperties {
  private boolean debug = false;

}
