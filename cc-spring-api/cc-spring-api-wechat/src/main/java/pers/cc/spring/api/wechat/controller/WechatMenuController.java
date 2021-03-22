package pers.cc.spring.api.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.model.menu.Button;
import pers.cc.spring.api.wechat.model.menu.Menu;
import pers.cc.spring.api.wechat.service.WechatMenuService;
import pers.cc.spring.core.message.Message;

/**
 * com.cc.api.wechat.controller
 *
 * @author chengce
 * @version 2017-10-25 16:43
 */
@RestController
@RequestMapping("${api.version}/auth/wechat")
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatMenuController {
  @Autowired
  WechatMenuService wechatMenuService;

  @RequestMapping(value = "menu", method = RequestMethod.GET)
  public Message createMenu() {
    Button button = new Button();
    button.setName("官网");
    button.setType(Button.ButtonType.view);
//        button.setUrl("http://pay.cqsiju.com?/#/wx/pay/test");
    button.setUrl("http://wx.shitidian.vip");

    return wechatMenuService.createNormalMenu(new Menu(new Button[]{button}, "testPay"));
  }

  @RequestMapping(value = "menu", method = RequestMethod.POST)
  public Message createMenu(@RequestBody Menu menu) {
    return wechatMenuService.createNormalMenu(menu);
  }
}
