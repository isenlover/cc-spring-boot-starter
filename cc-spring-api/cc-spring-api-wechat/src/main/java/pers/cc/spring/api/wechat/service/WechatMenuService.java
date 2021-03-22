package pers.cc.spring.api.wechat.service;


import pers.cc.spring.api.wechat.model.menu.*;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;
import pers.cc.spring.core.message.Message;

/**
 * Created by iSen on 2016/11/3
 */
public interface WechatMenuService {


  /**
   * 创建菜单
   *
   * @param menu 菜单
   * @return Message
   */
  Message<Menu> createNormalMenu(Menu menu);

  /**
   * 创建菜单
   *
   * @param buttons 一级菜单，不超过3个
   * @return Message
   */
  Message<Menu> createNormalMenu(Button[]... buttons);

  /**
   * 创建自定义菜单
   *
   * @param menu 菜单
   * @return Message
   */
  Message<ConditionalMenu> createConditionalMenu(ConditionalMenu menu);

  /**
   * 创建自定义菜单
   *
   * @param matchRule 匹配规则
   * @param buttons   一级菜单，不超过3个
   * @return Message
   */
  Message<ConditionalMenu> createConditionalMenu(MatchRule matchRule, Button[]... buttons);

  /**
   * 获取所有菜单
   *
   * @return Message<MenuMessage>
   */
  Message<MenuMessage> getAllMenu();

  /**
   * 删除所有包括个性化菜单
   *
   * @return Message<Integer>
   */
  Message<WxBaseResponse> deleteAllMenu();

  /**
   * 删除个性化菜单
   *
   * @param menuID 菜单ID 通过查询可以获得
   * @return Message<Integer>
   */
  Message<WxBaseResponse> deleteConditionalMenu(String menuID);

  /**
   * 查询此用户能看到的菜单
   *
   * @param openID 用户唯一标识
   * @return Message<Menu>
   */
  Message<MenuMessage> getUserMenus(String openID);
}
