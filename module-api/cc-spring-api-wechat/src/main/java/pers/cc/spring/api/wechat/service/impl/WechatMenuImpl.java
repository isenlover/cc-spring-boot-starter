package pers.cc.spring.api.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.enums.url.WechatMenuUrl;
import pers.cc.spring.api.wechat.model.menu.*;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;
import pers.cc.spring.api.wechat.model.user.UserMessage;
import pers.cc.spring.api.wechat.service.WechatMenuService;
import pers.cc.spring.api.wechat.token.WechatTokenManager;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.message.Message;

/**
 * Created by iSen on 2016/11/3
 */
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatMenuImpl implements WechatMenuService {

  @Autowired
  WechatTokenManager wechatTokenManager;

  public Message<Menu> createNormalMenu(Menu menu) {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatMenuUrl.createNormalUrl.getUrl());
    return WechatUtil.httpsPostWechat(requestUrl, JSON.toJSONString(menu), Menu.class);
  }

  public Message<Menu> createNormalMenu(Button[]... buttons) {
    Menu menu = new Menu();
    for (Button[] menuButtons : buttons) {
      menu.setButton(menuButtons);
    }
    return createNormalMenu(menu);
  }

  public Message<ConditionalMenu> createConditionalMenu(ConditionalMenu menu) {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatMenuUrl.createConditionalUrl.getUrl());
    return WechatUtil.httpsPostWechat(requestUrl, JSON.toJSONString(menu), ConditionalMenu.class);
  }

  public Message<ConditionalMenu> createConditionalMenu(MatchRule matchRule, Button[]... buttons) {
    ConditionalMenu conditionalMenu = new ConditionalMenu();
    conditionalMenu.setMatchrule(matchRule);
    for (Button[] menuButtons : buttons) {
      conditionalMenu.setButton(menuButtons);
    }
    return createConditionalMenu(conditionalMenu);
  }

  public Message<MenuMessage> getAllMenu() {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatMenuUrl.getAllUrl.getUrl());
    return WechatUtil.httpsGetWechat(requestUrl, MenuMessage.class);
  }

  public Message<WxBaseResponse> deleteAllMenu() {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatMenuUrl.deleteAllUrl.getUrl());
    return WechatUtil.httpsGetWechat(requestUrl, WxBaseResponse.class);
  }

  public Message<WxBaseResponse> deleteConditionalMenu(String menuID) {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatMenuUrl.deleteConditionalUrl.getUrl());
    return WechatUtil.httpsPostWechat(requestUrl, JSON.toJSONString(new Menu(menuID)), WxBaseResponse.class);
  }

  public Message<MenuMessage> getUserMenus(String openID) {
    String requestUrl = wechatTokenManager.getRealUrlReplaceAccessToken(WechatMenuUrl.getUserMenusUrl.getUrl());
    return WechatUtil.httpsPostWechat(requestUrl, JSON.toJSONString(new UserMessage(openID, "")), MenuMessage.class);
  }
}
