package pers.cc.spring.api.nim.model.team;

import lombok.Data;

/**
 * @author chengce
 * @version 2021-04-16 20:44
 */
@Data
public class NIMTeamHistoryDTO {
  /**
   * 群id
   */
  private String tid;

  /**
   * 查询用户对应的accid.
   */
  private String accid;

  /**
   * 开始时间，毫秒级
   */
  private long begintime;

  /**
   * 截止时间，毫秒级
   */
  private long endtime;

  /**
   * 本次查询的消息条数上限(最多100条),小于等于0，或者大于100，会提示参数错误
   */
  private int limit;

  /**
   * 1按时间正序排列，2按时间降序排列。其它返回参数414错误。默认是按降序排列，即时间戳最晚的消息排在最前面。
   */
  private int reverse;

  /**
   * 查询指定的多个消息类型，类型之间用","分割，不设置该参数则查询全部类型消息格式示例： 0,1,2,3
   * 类型支持： 1:图片，2:语音，3:视频，4:地理位置，5:通知，6:文件，10:提示，11:Robot，100:自定义
   */
  private String type;

  /**
   * true(默认值)：表示需要检查群是否有效,accid是否为有效的群成员；设置为false则仅检测群是否存在，accid是否曾经为群成员。
   */
  private boolean checkTeamValid;
}
