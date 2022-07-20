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

  /**
   * 极光推送是否推送生产环境，默认false
   */
  private boolean jPushProduction = false;

  /**
   * 是否输出详细的异常
   */
  private boolean exceptionMessage = false;

  /**
   * 是否控制台打印异常
   */
  private boolean exceptionPrint = false;
}
