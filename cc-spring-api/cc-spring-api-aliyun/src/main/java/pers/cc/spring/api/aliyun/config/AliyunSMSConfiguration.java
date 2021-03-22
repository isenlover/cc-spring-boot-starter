package pers.cc.spring.api.aliyun.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.api.aliyun.bean.AliyunSmsInformation;
import pers.cc.spring.core.scanner.Scan;

import java.util.Map;

/**
 * @author chengce
 * @version 2018-06-14 22:35
 */
public class AliyunSMSConfiguration {

  @Bean
  public AliyunSmsInformation aliyunSmsInformation() {
    AliyunSmsInformation aliyunSmsInformation = new AliyunSmsInformation();
    Map<String, Object> map = JSON.parseObject(System.getProperty("aliyunSMSApp"));
    aliyunSmsInformation.setSign((String) map.get("sign"));
    aliyunSmsInformation.setParam((String) map.get("param"));
    aliyunSmsInformation.setTemplate((String) map.get("template"));
    aliyunSmsInformation.setExpiration((int) map.get("expiration"));
    aliyunSmsInformation.setTimeout((int) map.get("timeout"));
    return aliyunSmsInformation;
  }

}
