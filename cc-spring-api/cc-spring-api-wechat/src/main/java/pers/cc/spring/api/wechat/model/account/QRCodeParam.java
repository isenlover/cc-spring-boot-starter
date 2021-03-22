package pers.cc.spring.api.wechat.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.cc.spring.api.wechat.enums.ActionName;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.account
 *
 * @link https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
 * @author chengce
 * @version 2017-10-24 21:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeParam {

  /**
   * 临时二维码参数
   */
  private int expire_seconds;

  private QRCodeParamScene scene;

  private ActionName actionName;
}
