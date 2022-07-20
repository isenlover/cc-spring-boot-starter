package pers.cc.spring.api.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pers.cc.spring.api.wechat.enums.url.WechatUserUrl;
import pers.cc.spring.api.wechat.model.user.UserMessage;
import pers.cc.spring.api.wechat.service.WechatUserService;
import pers.cc.spring.api.wechat.service.WechatUserTagService;
import pers.cc.spring.api.wechat.token.WechatTokenManager;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.message.Message;

/**
 * @author chengce
 * @version 2016-11-17 18:12
 */
@Slf4j
public abstract class WechatUserTagAbstract implements WechatUserTagService, WechatUserService {

  @Autowired
  WechatTokenManager wechatTokenManager;

  public Message<UserMessage> getUser(String openId) {
    log.info("wechatTokenManager：" + wechatTokenManager.toString());
    return getUser(openId, "zh_CN");
  }

  public Message<UserMessage> getUser(String openId, String language) {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatUserUrl.getBaseInfoUrl.getUrl());
    requestUrl = requestUrl.replace("zh_CN", language).replace("OPENID", openId);
    return WechatUtil.httpsGetWechat(requestUrl, UserMessage.class);
  }

  public Message<UserMessage> editUserRemark(String openId, String remark) {
    Message<UserMessage> httpMessage;
    UserMessage userMessage = new UserMessage();
    userMessage.setOpenid(openId);
    userMessage.setRemark(remark);
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatUserUrl.editUserRemarkUrl.getUrl());
    httpMessage = WechatUtil.httpsPostWechat(requestUrl, JSON.toJSONString(userMessage), UserMessage.class);
    if (httpMessage.isSuccess()) {
      httpMessage.setData(userMessage);
    }
    return httpMessage;
  }
}
