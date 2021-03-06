package pers.cc.spring.api.aliyun.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2018-07-12 11:57
 */
@Data
@ConfigurationProperties(
    prefix = "aliyun"
)
public class AliyunProperties {
  private boolean production = false;
}
