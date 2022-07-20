package pers.cc.spring.api.aliyun.oss.config;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.api.aliyun.oss.annotation.AliyunOSSParam;
import pers.cc.spring.api.aliyun.oss.bean.AliyunOSSParamBean;
import pers.cc.spring.core.util.other.ClassUtils;

/**
 * @author chengce
 * @version 2021-03-23 17:56
 */
@Configuration
@ComponentScan("pers.cc.spring.api.aliyun.oss")
@ConditionalOnBean(annotation = AliyunOSSParam.class)
public class AliyunOSSConfiguration {

  @Bean
  public AliyunOSSParamBean aliyunOSSParamBean() {
    return ClassUtils.mapToObject(JSON.parseObject(System.getProperty("AliyunOSSParam")), AliyunOSSParamBean.class);
  }
}
