package pers.cc.spring.api.aliyun.alipay.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.api.aliyun.alipay.bean.AlipayParamBean;
import pers.cc.spring.core.util.other.ClassUtils;

/**
 * @author chengce
 * @version 2021-03-23 18:05
 */
@Configuration
@ComponentScan("pers.cc.spring.api.aliyun.alipay")
public class AlipayConfiguration {

  @Bean
  public AlipayParamBean aliPayApp() {
    return ClassUtils.mapToObject(JSON.parseObject(System.getProperty("AliPayApp")), AlipayParamBean.class);
  }
}
