package pers.cc.spring.api.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengce
 * @version 2018-06-23 15:32
 */
@Data
@ConfigurationProperties(prefix = "spring.api.core")
public class ApiCoreProperties {
    /**
     * swagger 扫描包范围
     */
    private String packagePath = "pers";
}
