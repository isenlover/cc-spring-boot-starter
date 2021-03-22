package pers.cc.spring.amqp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2018-07-12 19:00
 */
@Data
@ConfigurationProperties(
    prefix = "cc.amqp"
)
public class AmqpProperties {
  /**
   * 如果为false，则所有配置均会失效
   */
  private boolean single;
  private String username = "guest";
  private String password = "guest";
  private String host = "localhost";
  private int port = 5672;
  /**
   * RPC机制下回复超时时间
   */
  private int replayTime = 5000;
}