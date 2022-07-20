package pers.cc.spring.api.aliyun.common.config;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.api.aliyun.common.annotation.AliyunParam;
import pers.cc.spring.api.aliyun.common.bean.AliyunParamBean;
import pers.cc.spring.api.aliyun.common.properties.AliyunProperties;
import pers.cc.spring.core.util.other.ClassUtils;

/**
 * @author chengce
 * @version 2021-03-23 18:05
 */
@Configuration
@EnableConfigurationProperties(
    value = {
        AliyunProperties.class
    }
)
public class AliyunConfiguration {

  @Bean
  public AliyunParamBean aliyunParamBean() {
    return ClassUtils.mapToObject(JSON.parseObject(System.getProperty("AliyunParam")), AliyunParamBean.class);
  }
}
