package pers.cc.spring.api.aliyun.sms.config;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.api.aliyun.sms.annotation.AliyunSMSParam;
import pers.cc.spring.api.aliyun.sms.bean.AliyunSMSParamBean;
import pers.cc.spring.core.util.other.ClassUtils;

/**
 * @author chengce
 * @version 2021-03-23 17:56
 */
@Configuration
@ComponentScan("pers.cc.spring.api.aliyun.sms")
@ConditionalOnBean(annotation = AliyunSMSParam.class)
public class AliyunSMSConfiguration {

  @Bean
  public AliyunSMSParamBean aliyunSMSParamBean() {
    return ClassUtils.mapToObject(JSON.parseObject(System.getProperty("AliyunSMSParam")), AliyunSMSParamBean.class);
  }
}
