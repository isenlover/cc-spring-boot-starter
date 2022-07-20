package pers.cc.spring.api.wechat.config;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.bean.WechatInformation;
import pers.cc.spring.core.scanner.Scan;

import java.util.Map;

/**
 * @author chengce
 * @version 2018-06-01 18:05
 * @fixed 2021-03-18
 */
@ComponentScan(Scan.PACKAGE_CC_SPRING_API_WECHAT)
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatConfiguration {

  @Bean
  public WechatInformation wxApp() {
    WechatInformation wechatInformation = new WechatInformation();
    Map<String, Object> map = JSON.parseObject(System.getProperty("wechatApp"));
    wechatInformation.setAppId((String) map.get("appId"));
    wechatInformation.setAppSecret((String) map.get("appSecret"));
    wechatInformation.setMerchantId((String) map.get("merchantId"));
    wechatInformation.setMerchantKey((String) map.get("merchantKey"));
    wechatInformation.setToken((String) map.get("token"));
    wechatInformation.setId((String) map.get("id"));
    return wechatInformation;
  }
}
