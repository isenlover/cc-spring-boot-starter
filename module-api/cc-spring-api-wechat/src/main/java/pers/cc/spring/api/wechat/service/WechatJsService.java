package pers.cc.spring.api.wechat.service;

import pers.cc.spring.api.wechat.model.js.WxJsConfigParamVO;
import pers.cc.spring.api.wechat.model.js.WxJsRequestParamDTO;
import pers.cc.spring.api.wechat.model.js.oauth.WxJsOAuthDTO;
import pers.cc.spring.api.wechat.model.js.oauth.WxJsOAuthAccessToken;
import pers.cc.spring.api.wechat.model.js.oauth.WxJsOAuthUserInformation;
import pers.cc.spring.core.message.Message;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 微信Js开发相关接口
 *
 * @author chengce
 * @version 2017-11-13 20:26
 */
public interface WechatJsService {

  /**
   * 获取JS Ticket
   * 有效期：2小时
   */
  void getJSTicket();

  /**
   * 获取js验证
   * 服务器获取code目前还未正常获取过
   *
   * @param wxJsOAuthDTO js验证
   */
  @Deprecated
  void jsOAuth(WxJsOAuthDTO wxJsOAuthDTO);

  /**
   * 获取用户授权
   * 第一步
   *
   * @param wxJsOAuthDTO js验证
   * @return 验证url
   */
  Message<String> getAuthorizedUrl(WxJsOAuthDTO wxJsOAuthDTO);

  /**
   * 获取用户授权
   * 第二步
   *
   * @param code js获取的code
   * @return openid相关信息
   */
  Message<WxJsOAuthAccessToken> getAccessToken(String code);


  /**
   * 获取用户授权
   * 第三步
   * 返回用户微信信息
   *
   * @param accessToken js的accessToken
   * @param openId      js的openid
   * @return 用户信息
   */
  Message<WxJsOAuthUserInformation> getUserInformation(String accessToken, String openId);

  /**
   * 获取 微信前端js config参数
   *
   * @param wxJsRequestParam 微信前端传来的参数
   * @return jsconfig参数
   * @throws UnsupportedEncodingException 编码异常
   * @throws NoSuchAlgorithmException     母鸡
   */
  Message<WxJsConfigParamVO> getJsConfigParam(WxJsRequestParamDTO wxJsRequestParam) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
