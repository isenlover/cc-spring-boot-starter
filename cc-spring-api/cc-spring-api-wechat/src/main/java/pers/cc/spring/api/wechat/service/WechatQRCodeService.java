package pers.cc.spring.api.wechat.service;

import pers.cc.spring.api.wechat.model.account.QRCodeParam;
import pers.cc.spring.api.wechat.model.account.QRCodeResp;
import pers.cc.spring.core.message.Message;

/**
 * com.cc.api.wechat.service.account
 * 微信-账号管理接口
 *
 * @author chengce
 * @version 2017-10-24 20:46
 */
public interface WechatQRCodeService {

  Message<QRCodeResp> getQRCode(QRCodeParam params);

  String getQRCodeImage(QRCodeParam params);
}
