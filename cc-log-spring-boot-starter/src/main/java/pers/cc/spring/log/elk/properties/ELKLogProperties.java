package pers.cc.spring.log.elk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2019-01-03 02:14
 */
@Data
@ConfigurationProperties(
    prefix = "log.elk"
)
public class ELKLogProperties {

  private String app = "logstash";

  @Deprecated
  private String remoteHost = "localhost";

  @Deprecated
  private String remotePort = "4560";

  private String destination = "localhost:4560";

  @Deprecated
  private boolean open = true;
}
