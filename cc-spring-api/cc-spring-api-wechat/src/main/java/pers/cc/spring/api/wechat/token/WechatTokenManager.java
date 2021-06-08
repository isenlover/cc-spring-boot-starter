package pers.cc.spring.api.wechat.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pers.cc.spring.data.redis.service.RedisService;

import java.util.concurrent.TimeUnit;

/**
 * @author chengce
 * @version 2021-06-09 00:10
 */
@Configuration
public class WechatTokenManager {

  @Autowired
  RedisService redisService;

  public void saveToken(String accessToken) {
    redisService.cacheValue("Wechat-Token", accessToken, 110, TimeUnit.MINUTES);
  }

  public String getToken() {
    return redisService.getValue("Wechat-Token");
  }

  public String getRealUrlReplaceAccessToken(String url) {
    return url.replace("ACCESS_TOKEN", getToken());
  }

}
