package pers.cc.spring.api.nim.model.chatroom;

import lombok.Data;

/**
 * com.cc.api.netease.model.chatroom
 *
 * @author chengce
 * @version 2018-01-07 03:07
 */
@Data
public class ChatroomCreateRespDetail {
  private String roomid;

  private String valid;

  private String announcement;

  private String name;

  private String broadcasturl;

  private String ext;

  private String creator;
}
