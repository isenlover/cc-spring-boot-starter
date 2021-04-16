package pers.cc.spring.api.nim.config;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.api.nim.annotation.EnableNim;
import pers.cc.spring.api.nim.model.NimInformation;

import java.util.Map;

/**
 * @author chengce
 * @version 2018-06-01 18:05
 * @fixed 2021-03-18
 */
@ComponentScan("pers.cc.spring.api.nim.*")
@ConditionalOnBean(annotation = EnableNim.class)
public class NimConfiguration {

  @Bean
  public NimInformation nimInformation() {
    NimInformation nimInformation = new NimInformation();
    Map<String, Object> map = JSON.parseObject(System.getProperty("NimApp"));
    nimInformation.setAppKey((String) map.get("appKey"));
    nimInformation.setAppSecret((String) map.get("appSecret"));
    return nimInformation;
  }
}
