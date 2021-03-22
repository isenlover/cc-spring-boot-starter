package pers.cc.spring.api.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.model.other.AuthenticationBo;
import pers.cc.spring.api.wechat.service.WechatCoreService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 应用服务器和微信通信controller
 * 微信会通过GET请求验证
 * 收到用户消息会POST返回
 * <p>
 * 因微信不允许重定向，所以POST controller要自己编写逻辑
 *
 * @author chengce
 * @version 2016-04-26 16:13
 * update 2017-10-24
 */
@Slf4j
@Controller
@RequestMapping("${api.version}/auth/wechat")
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatAuthenticateController {

  @Autowired
  private WechatCoreService wechatCoreService;

  /**
   * 验证微信Url
   *
   * @param authenticationBo    签名业务对象
   * @param httpServletResponse http响应
   */
  @GetMapping
  public void wechat(AuthenticationBo authenticationBo, HttpServletResponse httpServletResponse) {
    try {
      PrintWriter printWriter = httpServletResponse.getWriter();
      try {
        if (wechatCoreService.checkSignature(authenticationBo)) {
          printWriter.print(authenticationBo.getEchostr());
        }
      } catch (NoSuchAlgorithmException e) {
        log.error("验证签名异常:  " + e.getMessage());
      }
      printWriter.close();
    } catch (IOException e) {
      log.error("验证签名异常:  " + e.getMessage());
    }
  }


//    /**
//     * 接收微信消息，  不能重定向
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "wechat", method = RequestMethod.POST)
//    public ModelAndView wechat(HttpServletRequest request) {
//        Map<String, String> map = WechatUtil.xmlToMap(request);
//        if (map.get("Event").equals("subscribe")) {
//            CCUtil.printLn("重定向" + apiVersion);
//            return new ModelAndView("redirect:/api/v1/auth/wechat/callback/subscribe");
//        } else {
//            return null;
//        }
//    }
}
