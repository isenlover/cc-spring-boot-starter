package pers.cc.spring.api.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.bean.WechatInformation;
import pers.cc.spring.api.wechat.model.other.AccessTokenBo;
import pers.cc.spring.api.wechat.model.other.AuthenticationBo;
import pers.cc.spring.api.wechat.service.WechatCoreService;
import pers.cc.spring.api.wechat.service.WechatSignService;
import pers.cc.spring.core.util.CLog;
import pers.cc.spring.core.util.http.HttpUtils;

import java.security.NoSuchAlgorithmException;

/**
 * Created by CC on 2016-04-26 下午4:15
 */
@Slf4j
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatCoreImpl implements WechatCoreService {

  @Autowired
  WechatInformation wechatAppBean;

  @Autowired
  WechatSignService wechatSignService;

  @Autowired
  CLog cLog;

  public enum CCWechat {

    /**
     * Token
     */
    ACCESS_TOKEN("access_token"),

    /**
     * Token有效期
     */
    EXPIRES_IN("expires_in"),

    /**
     * 微信appID
     */
    APPID("APPID"),

    /**
     * 微信app secretKey
     */
    APPSECRET("APPSECRET"),

    /**
     * 获取ACCESS TOKEN URL
     */
    ACCESSTOKENURLVALUE("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET");

    private String weChartName;

    CCWechat(String weChartName) {
      this.weChartName = weChartName;
    }

    public String getString() {
      return weChartName;
    }
  }

  public boolean checkSignature(AuthenticationBo authenticationBo) throws NoSuchAlgorithmException {
    return wechatSignService.checkSignature(authenticationBo.getSignature(), authenticationBo.getTimestamp(),
        authenticationBo.getNonce());
  }

  public void initAccessToken() {
    String requestUrl = CCWechat.ACCESSTOKENURLVALUE.getString()
        .replace(CCWechat.APPID.getString(), wechatAppBean.getAppId())
        .replace(CCWechat.APPSECRET.getString(), wechatAppBean.getAppSecret());

    String response;
    try {
      response = HttpUtils.httpsGet(requestUrl);
      log.info("initAccessToken: " + response);
      AccessTokenBo.setAccessTokenBo(JSON.parseObject(response, AccessTokenBo.class));
    } catch (Exception e) {
      log.error("initAccessToken-error: " + e.getLocalizedMessage());
      e.printStackTrace();
    }
  }
}
