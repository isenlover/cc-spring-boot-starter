package pers.cc.spring.api.wechat.model;

import lombok.Data;

/**
 * @author chengce
 * @version 2021-03-18 22:48
 */
@Data
public class WechatOfficialAccountReceiveVO {
  private String ticket;

  private String createTime;

  private String event;

  private String eventKey;
  
  private String toUserName;

  private String fromUserName;

  private String msgType;

  private String msgId;

  private String content;
}
