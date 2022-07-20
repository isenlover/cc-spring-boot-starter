package pers.cc.spring.api.wechat.app.model.dto;

import lombok.Data;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * 微信AccessToken  与 AccessTokenDTO 有区别
 * 此类主要接受第三方APP或WEB登录 通过code获取AccessToken 的返回值
 * <p>
 * 参考:
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=2f588cb97e80263c721d035d349e9d737713da5b&lang=zh_CN
 *
 * @author chengce
 * @version 2017-07-10 下午8:05
 */
@Data
public class AccessTokenCode extends WxBaseResponse {
  /**
   * 接口调用凭证
   */
  private String access_token;
  /**
   * access_token接口调用凭证超时时间，单位（秒）
   */
  private int expires_in;
  /**
   * 用户刷新access_token
   */
  private String refresh_token;
  /**
   * 授权用户唯一标识
   */
  private String openid;
  /**
   * 用户授权的作用域，使用逗号（,）分隔
   */
  private String scope;
}
