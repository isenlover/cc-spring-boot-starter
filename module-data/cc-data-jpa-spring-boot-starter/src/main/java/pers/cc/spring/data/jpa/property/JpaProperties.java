package pers.cc.spring.data.jpa.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2021-03-25 12:17
 */
@Data
@ConfigurationProperties(prefix = "cc.jpa")
public class JpaProperties {
  private int snowflakeWorkId = 7;

  private int snowflakeMachineId = 7;
}
