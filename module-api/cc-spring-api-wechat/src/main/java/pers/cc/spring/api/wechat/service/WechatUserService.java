package pers.cc.spring.api.wechat.service;


import pers.cc.spring.core.message.Message;
import pers.cc.spring.api.wechat.model.user.UserMessage;

/**
 * Created by iSen on 2016/11/10
 */
public interface WechatUserService {

  /**
   * 返回用户信息
   *
   * @param openId 唯一标识
   * @return User
   */
  Message<UserMessage> getUser(String openId);

  /**
   * 返回用户信息
   *
   * @param openId   唯一标识
   * @param language zh_CN 简体，zh_TW 繁体，en 英语
   * @return User
   */
  Message<UserMessage> getUser(String openId, String language);

  /**
   * 设置用户备注名
   *
   * @param openId 唯一标识
   * @param remark 新的备注名，长度必须小于30字符
   * @return Message<User>
   */
  Message<UserMessage> editUserRemark(String openId, String remark);
}
