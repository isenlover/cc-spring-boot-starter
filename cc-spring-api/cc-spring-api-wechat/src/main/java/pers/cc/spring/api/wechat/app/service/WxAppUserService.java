package pers.cc.spring.api.wechat.app.service;


import pers.cc.spring.api.wechat.app.model.dto.AccessTokenCode;
import pers.cc.spring.api.wechat.app.model.dto.WxUserInfoDTO;
import pers.cc.spring.core.message.Message;

/**
 * 一般用于移动端的获取用户信息接口
 *
 * @author chengce
 * @version 2017-07-10 下午8:21
 */
public interface WxAppUserService {

  /**
   * 通过Code获取AccessToken
   *
   * @param code code
   * @return accessToken详情DTO
   */
  Message<AccessTokenCode> getAccessToken(String code);

  /**
   * 刷新某个用户的accessToken
   *
   * @param openId 用户唯一标识
   * @return accessToken详情DTO
   */
  Message<AccessTokenCode> refreshToken(String openId, String refreshToken);

  /**
   * 获取用户信息类
   *
   * @param accessToken 唯一标识
   * @param openid      用户唯一标识
   * @return 用户信息类
   */
  Message<WxUserInfoDTO> getUserInfo(String accessToken, String openid);

  /**
   * 获取用户信息类,通过openid查找redis缓存的其他参数，如果没有则重新获取，过期则刷新
   *
   * @param openId 用户唯一标识
   * @return 用户信息类
   */
  Message getUserInfo(String openId);

  /**
   * 获取用户信息类
   *
   * @param accessToken 唯一标识
   * @param openid      用户唯一标识
   * @param lang        国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
   * @return 用户信息类
   */
  Message<WxUserInfoDTO> getUserInfo(String accessToken, String openid, String lang);
}
