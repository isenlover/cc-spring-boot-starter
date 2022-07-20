package pers.cc.spring.api.nim.enums;

/**
 * @author chengce
 * @version 2021-04-16 18:49
 */
public enum NimUrl {
  /**
   * 创建用户
   */
  CREATE_USER("https://api.netease.im/nimserver/user/create.action"),

  UPDATE_USER("https://api.netease.im/nimserver/user/updateUinfo.action"),

  /**
   * 创建聊天室
   */
  CREATE_CHAT_ROOM("https://api.netease.im/nimserver/chatroom/create.action"),

  /**
   * 发送批量普通消息
   */
  SEND_BATCH_MSG("https://api.netease.im/nimserver/msg/sendBatchMsg.action"),

  /**
   * 发送批量自定义消息
   */
  SEND_BATCH_ATTACH_MSG("https://api.netease.im/nimserver/msg/sendBatchAttachMsg.action"),

  /**
   * 查询群组聊天记录
   */
  QUERY_TEAM_MSG_HISTORY("https://api.netease.im/nimserver/history/queryTeamMsg.action"),

  /**
   * 创建群组(高级)
   */
  CREATE_TEAM("https://api.netease.im/nimserver/team/create.action"),

  /**
   * 拉人入群
   */
  JOIN_TEAM("https://api.netease.im/nimserver/team/add.action"),
  ;

  private final String description;

  NimUrl(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return this.name() + "(" + description + ")";
  }
}
