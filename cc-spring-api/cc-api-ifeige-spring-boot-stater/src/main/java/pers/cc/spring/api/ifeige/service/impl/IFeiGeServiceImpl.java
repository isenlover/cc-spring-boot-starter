package pers.cc.spring.api.ifeige.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.ifeige.model.IFeiGeData;
import pers.cc.spring.api.ifeige.model.IFeiGeMessage;
import pers.cc.spring.api.ifeige.model.IFeiGeResponse;
import pers.cc.spring.api.ifeige.model.IFeiGeValue;
import pers.cc.spring.api.ifeige.properties.IFeiGeApiProperties;
import pers.cc.spring.api.ifeige.service.IFeiGeService;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.http.HttpUtils;
import pers.cc.spring.core.util.other.DateUtils;

import java.util.HashMap;
import java.util.Map;

import static pers.cc.spring.core.util.other.DateUtils.dateTimeFormat;

/**
 * @author chengce
 * @version 2020-03-01 16:18
 */
@Service
public class IFeiGeServiceImpl implements IFeiGeService {

  @Autowired
  IFeiGeApiProperties iFeiGeApiProperties;

  @Override
  public Message<IFeiGeResponse> sendMessage(IFeiGeMessage message) throws Exception {
    if (CommonUtils.isEmpty(message.getApp_key())) {
      message.setApp_key(iFeiGeApiProperties.getAppKey());
      message.setSecret(iFeiGeApiProperties.getSecret());
      message.setTemplate_id(iFeiGeApiProperties.getTemplateId());
    }
    if (CommonUtils.isEmpty(message.getData().getOccurtime())) {
      message.getData().setOccurtime(new IFeiGeValue.Builder().value(DateUtils.getCurrentDayString(dateTimeFormat)).build());
    }
    Map<String, String> headers = new HashMap<>();
    headers.put("content-type", "application/json");
    String response = HttpUtils.httpsPost("https://u.ifeige.cn/api/message/send", JSON.toJSONString(message), headers);
    return Message.ok(JSON.parseObject(response, IFeiGeResponse.class));
  }

  @Override
  public Message<IFeiGeResponse> sendMessage(IFeiGeData data) throws Exception {
    return sendMessage(new IFeiGeMessage.Builder().data(data).build());
  }
}
