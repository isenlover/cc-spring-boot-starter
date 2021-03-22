package pers.cc.spring.api.aliyun.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.api.aliyun.bean.AliyunOSSInformation;
import pers.cc.spring.core.scanner.Scan;

import java.util.Map;

/**
 * @author chengce
 * @version 2018-06-14 22:35
 */
public class AliyunOSSConfiguration {

  @Bean
  public AliyunOSSInformation aliyunOSSInformation() {
    AliyunOSSInformation aliyunOSSInformation = new AliyunOSSInformation();
    Map<String, Object> map = JSON.parseObject(System.getProperty("aliyunOSSApp"));
    aliyunOSSInformation.setBucketName((String) map.get("bucketName"));
    aliyunOSSInformation.setIntranetEndpoint((String) map.get("intranetEndpoint"));
    aliyunOSSInformation.setInternetEndpoint((String) map.get("internetEndpoint"));
    aliyunOSSInformation.setUrl((String) map.get("url"));
    return aliyunOSSInformation;
  }

}
