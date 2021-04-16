package pers.cc.spring.api.nim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.nim.annotation.EnableNim;
import pers.cc.spring.api.nim.enums.NimUrl;
import pers.cc.spring.api.nim.exception.NIMPushException;
import pers.cc.spring.api.nim.model.push.NIMCustomPushDTO;
import pers.cc.spring.api.nim.model.push.NIMPushDTO;
import pers.cc.spring.api.nim.model.push.NIMPushResp;
import pers.cc.spring.api.nim.service.NIMPushService;
import pers.cc.spring.api.nim.util.NIMHelper;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.http.HttpUtils;

/**
 * com.cc.api.netease.service.impl
 *
 * @author chengce
 * @version 2018-01-08 14:44
 */
@Service
@ConditionalOnBean(annotation = EnableNim.class)
public class NIMPushServiceImpl implements NIMPushService {

  @Autowired
  private NIMHelper nimHelper;

  @Override
  public Message<NIMPushResp> sendBatchMessage(NIMPushDTO pushDTO) throws NIMPushException {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.SEND_BATCH_MSG.getDescription()
          , HttpUtils.getUrlParamsByObject(pushDTO)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMPushException();
    }
    return nimHelper.checkResponse(resp, NIMPushResp.class);
  }

  @Override
  public Message<NIMPushResp> sendBatchMessage(NIMCustomPushDTO pushDTO) throws NIMPushException {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.SEND_BATCH_ATTACH_MSG.getDescription()
          , HttpUtils.getUrlParamsByObject(pushDTO)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMPushException();
    }
    return nimHelper.checkResponse(resp, NIMPushResp.class);
  }
}
