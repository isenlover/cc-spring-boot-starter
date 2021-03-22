package pers.cc.spring.api.wechat.model.response;


import lombok.Data;
import pers.cc.spring.api.wechat.enums.WechatMessageType;

@Data
public class TextMessage extends BaseMessage {
  // 回复的消息内容
  private String Content;

  public TextMessage() {
    this.setMsgType(WechatMessageType.response_text.getType());
  }
}
