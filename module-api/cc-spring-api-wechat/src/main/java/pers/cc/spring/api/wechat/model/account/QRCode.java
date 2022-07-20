package pers.cc.spring.api.wechat.model.account;

import lombok.Data;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.account
 * <p>
 * 二维码
 *
 * @author chengce
 * @version 2017-10-24 20:54
 */
@Data
public class QRCode {
  /**
   * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
   */
  private long expire_seconds;

  /**
   * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
   */
  private String action_name;

  /**
   * 二维码详细信息
   */
  private QRCodeParam action_info;

  /**
   * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
   */
  private String scene_id;

  /**
   * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
   */
  private String scene_str;
}
