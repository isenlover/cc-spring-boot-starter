package pers.cc.spring.api.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pers.cc.spring.api.wechat.service.WechatJsService;

/**
 * com.cc.api.wechat.controller
 * <p>
 * weixin js controller
 *
 * @author chengce
 * @version 2016-04-26 16:13
 * update 2017-10-24
 */
//@Controller
//@RequestMapping("${api.version}/auth/wechat/js")
public class WechatJsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatJsService wechatJsService;

    /**
     * 获取js授权，用于获取Openid等
     *
     * @param scopeType 作用域
     */
//    @RequestMapping(value = "oauth", method = RequestMethod.GET)
//    public ResponseEntity jsOauth(@RequestParam(value = "scopeType", defaultValue = "snsapi_userinfo", required = false) WxJsOAuthDTO.ScopeType scopeType) {
//        WxJsOAuthDTO wxJsOAuthDTO = new WxJsOAuthDTO();
//        wxJsOAuthDTO.setScope(scopeType);
//        wxJsOAuthDTO.setRedirect_uri("http://wx.shitidian.vip/#/auth/help/user/register");
////        wxJsOAuthDTO.setState(user.getAccount());
//        logger.info("正在js获取授权");
////        wechatJsService.jsOAuth(wxJsOAuthDTO);
//        return ResponseEntity.ok(wechatJsService.getAuthorizedUrl(wxJsOAuthDTO));
//    }


}
