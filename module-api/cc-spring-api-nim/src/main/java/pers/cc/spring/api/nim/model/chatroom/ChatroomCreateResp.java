package pers.cc.spring.api.nim.model.chatroom;

import pers.cc.spring.api.nim.model.response.NIMBaseResponse;
import lombok.Data;

/**
 * com.cc.api.netease.model
 *
 * @author chengce
 * @version 2018-01-07 03:03
 */
@Data
public class ChatroomCreateResp extends NIMBaseResponse {

  private ChatroomCreateRespDetail chatroom;

  public ChatroomCreateRespDetail getChatroom() {
    return chatroom;
  }

  public void setChatroom(ChatroomCreateRespDetail chatroom) {
    this.chatroom = chatroom;
  }
}
