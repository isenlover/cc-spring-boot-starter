package pers.cc.spring.api.aliyun.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.api.aliyun.bean.AliyunInformation;
import pers.cc.spring.core.scanner.Scan;

import java.util.Map;

/**
 * @author chengce
 * @version 2018-06-14 22:35
 */
@ComponentScan(Scan.PACKAGE_CC_SPRING_API_ALIYUN)
public class AliyunConfiguration {

  @Bean
  public AliyunInformation aliyunInformation() {
    AliyunInformation aliyunInformation = new AliyunInformation();
    Map<String, Object> map = JSON.parseObject(System.getProperty("aliyunApp"));
    aliyunInformation.setKey((String) map.get("key"));
    aliyunInformation.setSecret((String) map.get("secret"));
    return aliyunInformation;
  }
}
