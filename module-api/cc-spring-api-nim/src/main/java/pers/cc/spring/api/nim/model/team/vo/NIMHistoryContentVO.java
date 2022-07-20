package pers.cc.spring.api.nim.model.team.vo;

import lombok.Data;

/**
 * @author chengce
 * @version 2021-04-16 20:49
 */
@Data
public class NIMHistoryContentVO<T> {

  /**
   * 发送者accid
   */
  private String from;

  /**
   * msgid(long类型)为消息的服务端id
   */
  private String msgid;

  private long sendtime;

  /**
   *  1:图片，2:语音，3:视频，4:地理位置，5:通知，6:文件，10:提示，11:Robot，100:自定义
   */
  private int type;

  /**
   * 1：android、2:iOS、4：PC、16:WEB、32:REST、64:MAC
   */
  private String fromclienttype;

  private T body;
}
