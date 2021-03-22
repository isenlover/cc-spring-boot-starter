package pers.cc.spring.api.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.bean.WechatInformation;
import pers.cc.spring.api.wechat.model.js.WxJsConfigParamVO;
import pers.cc.spring.api.wechat.model.js.WxJsRequestParamDTO;
import pers.cc.spring.api.wechat.model.js.oauth.WxJsOAuthDTO;
import pers.cc.spring.api.wechat.model.js.oauth.WxJsOAuthAccessToken;
import pers.cc.spring.api.wechat.model.js.oauth.WxJsOAuthUserInformation;
import pers.cc.spring.api.wechat.model.other.AccessTokenBo;
import pers.cc.spring.api.wechat.model.other.JSTicketBo;
import pers.cc.spring.api.wechat.service.WechatJsService;
import pers.cc.spring.api.wechat.service.WechatSignService;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.CLog;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.http.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

/**
 * 微信js接口实现
 *
 * @author chengce
 * @version 2017-11-13 20:27
 */
@Slf4j
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatJsImpl implements WechatJsService {

  @Autowired
  WechatInformation wechatAppBean;

  @Autowired
  WechatSignService wechatSignService;

  @Autowired
  CLog cLog;

  @Value("${wechat.url.oauth.js}")
  private String url_js_oauth;

  @Value("${wechat.url.oauth.accessToken}")
  private String url_js_oauth_access_token;

  @Value("${wechat.url.oauth.userInfo}")
  private String url_js_oauth_userInfo;

  @Value("${wechat.url.js.ticket}")
  private String url_js_ticket;

  @Override
  public void getJSTicket() {
    if (CommonUtils.isNotEmpty(AccessTokenBo.getInstance().getAccess_token())) {
      String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(url_js_ticket);
      Message<JSTicketBo> message = WechatUtil.httpsGetWechat(requestUrl, JSTicketBo.class);
      cLog.info("getJSTicket: " + message.toString());
      if (CommonUtils.isNotEmpty(message.getData())) {
        JSTicketBo.setJsTicketBo(message.getData());
      }
    }
  }

  @Override
  public void jsOAuth(WxJsOAuthDTO wxJsOAuthDTO) {
//        WechatUtil.httpsGetWechat(getAuthorizedUrl(wxJsOAuthDTO), String.class);
  }

  @Override
  public Message<String> getAuthorizedUrl(WxJsOAuthDTO wxJsOAuthDTO) {
    wxJsOAuthDTO.setAppid(wechatAppBean.getAppId());
    wxJsOAuthDTO.setResponse_type("code");
    try {
      wxJsOAuthDTO.setRedirect_uri(URLEncoder.encode(wxJsOAuthDTO.getRedirect_uri(), "utf-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String params = null;
    try {
      params = HttpUtils.getUrlParamsByObject(wxJsOAuthDTO) + "#wechat_redirect";
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return Message.ok(url_js_oauth + "?" + params);
  }

  @Override
  public Message<WxJsOAuthAccessToken> getAccessToken(String code) {
    String url = url_js_oauth_access_token + "?appid=" + wechatAppBean.getAppId() + "&secret=" + wechatAppBean.getAppSecret() + "&code=" + code + "&grant_type=authorization_code";
    return WechatUtil.httpsGetWechat(url, WxJsOAuthAccessToken.class);
  }

  @Override
  public Message<WxJsOAuthUserInformation> getUserInformation(String accessToken, String openId) {
    String url = url_js_oauth_userInfo.replace("ACCESS_TOKEN", accessToken)
        .replace("OPENID", openId);
    return WechatUtil.httpsGetWechat(url, WxJsOAuthUserInformation.class);
  }

  @Override
  public Message<WxJsConfigParamVO> getJsConfigParam(WxJsRequestParamDTO wxJsRequestParam) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    if (CommonUtils.isNotEmpty(JSTicketBo.getInstance().getTicket())) {
      WxJsConfigParamVO wxJsConfigParam = wechatSignService.createJsSign(JSTicketBo.getInstance().getTicket(),
          wxJsRequestParam.getUrl());
      wxJsConfigParam.setAppId(wechatAppBean.getAppId());
      return Message.ok(wxJsConfigParam);
    } else {
      throw new BaseRuntimeException("ticket为空");
    }
  }
}
