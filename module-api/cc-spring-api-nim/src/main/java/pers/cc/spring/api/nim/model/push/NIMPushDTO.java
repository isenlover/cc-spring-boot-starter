package pers.cc.spring.api.nim.model.push;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * com.cc.api.netease.model.push
 * 推送消息
 * http://dev.netease.im/docs/product/IM%E5%8D%B3%E6%97%B6%E9%80%9A%E8%AE%AF/%E6%9C%8D%E5%8A%A1%E7%AB%AFAPI%E6%96%87%E6%A1%A3/%E6%B6%88%E6%81%AF%E5%8A%9F%E8%83%BD?#%E6%89%B9%E9%87%8F%E5%8F%91%E9%80%81%E7%82%B9%E5%AF%B9%E7%82%B9%E6%99%AE%E9%80%9A%E6%B6%88%E6%81%AF
 *
 * @author chengce
 * @version 2018-01-08 14:32
 */
@Data
public class NIMPushDTO {

  public static enum Type {
    Text, // 表示文本消息,
    Image, // 表示图片，
    Audio, // 表示语音，
    Video, // 表示视频，
    Location, // 表示地理位置信息，
    File, // 表示文件，
    Auto, // 自定义消息类型
  }

  /**
   * required
   * 发送者accid，用户帐号，最大32字符，
   * 必须保证一个APP内唯一
   */
  private String fromAccid;
  /**
   * required
   * ["aaa","bbb"]（JSONArray对应的accid，如果解析出错，会报414错误），限500人
   */
  private String toAccids;
  /**
   * required
   */
  private Type type;
  /**
   * required
   * 请参考下方消息示例说明中对应消息的body字段，
   * 最大长度5000字符，为一个json串
   */
  private String body;
  private String option;
  private String pushcontent;
  private String payload;
  private String ext;
  private String bid;

  @JSONField(serialize = false)
  public Type getType() {
    return type;
  }

  @JSONField(name = "type")
  public int getTypeJSON() {
    return type.ordinal();
  }
}
