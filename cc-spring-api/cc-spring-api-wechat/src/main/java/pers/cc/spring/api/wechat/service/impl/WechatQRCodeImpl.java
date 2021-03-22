package pers.cc.spring.api.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.enums.url.WechatQRCodeUrl;
import pers.cc.spring.api.wechat.model.account.QRCode;
import pers.cc.spring.api.wechat.model.account.QRCodeParam;
import pers.cc.spring.api.wechat.model.account.QRCodeResp;
import pers.cc.spring.api.wechat.service.WechatQRCodeService;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.message.Message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * com.cc.api.wechat.service.account.impl
 *
 * @author chengce
 * @version 2017-10-24 20:47
 */
@Primary
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatQRCodeImpl implements WechatQRCodeService {


  @Override
  public Message<QRCodeResp> getQRCode(QRCodeParam params) {
    QRCode qrCode = new QRCode();
    qrCode.setAction_name(params.getActionName().name());
    qrCode.setAction_info(params);
    String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(WechatQRCodeUrl.getUrl.getUrl());
    return WechatUtil.httpsPostWechat(requestUrl, JSON.toJSONString(qrCode), QRCodeResp.class);
  }

  @Override
  public String getQRCodeImage(QRCodeParam params) {
    Message<QRCodeResp> message = getQRCode(params);
    if (message.isSuccess()) {
      String qrUrl = null;
      try {
        qrUrl = WechatQRCodeUrl.getImageUrl.getUrl()
            .replace("TICKET", URLEncoder.encode(message.getData().getTicket(), "utf-8"));
//        qrUrl = URLEncoder.encode(qrUrl, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      return qrUrl;
//      Message<String> httpMessage = WechatUtil.httpsGetWechat(qrUrl, String.class);
//      if (httpMessage.isSuccess()) {
//        return httpMessage.getData();
//      }
    }
    return null;
  }
}
