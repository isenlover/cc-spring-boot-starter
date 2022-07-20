package pers.cc.spring.api.nim.model.push;

import lombok.Data;

/**
 * com.cc.api.netease.model.push
 *
 * @author chengce
 * @version 2018-01-08 14:48
 */
@Data
public class NIMCustomPushDTO {
  private String fromAccid;
  /**
   * required
   * ["aaa","bbb"]（JSONArray对应的accid，如果解析出错，会报414错误），限500人
   */
  private String toAccids;
  /**
   * required
   * 自定义通知内容，第三方组装的字符串，建议是JSON串，最大长度4096字符
   */
  private String attach;
  /**
   * iOS推送内容，第三方自己组装的推送内容,不超过150字符
   */
  private String pushcontent;
  /**
   * iOS推送对应的payload,必须是JSON,不能超过2k字符
   */
  private String payload;
  /**
   * 如果有指定推送，此属性指定为客户端本地的声音文件名，长度不要超过30个字符，如果不指定，会使用默认声音
   */
  private String sound;
  /**
   * 1表示只发在线，2表示会存离线，其他会报414错误。默认会存离线
   */
  private int save = 2;
  /**
   * 发消息时特殊指定的行为选项,Json格式，可用于指定消息计数等特殊行为;option中字段不填时表示默认值。
   * option示例：
   * {"badge":false,"needPushNick":false,"route":false}
   * 字段说明：
   * 1. badge:该消息是否需要计入到未读计数中，默认true;
   * 2. needPushNick: 推送文案是否需要带上昵称，不设置该参数时默认false(ps:注意与sendBatchMsg.action接口有别)。
   * 3. route: 该消息是否需要抄送第三方；默认true (需要app开通消息抄送功能)
   */
  private String option;
}
