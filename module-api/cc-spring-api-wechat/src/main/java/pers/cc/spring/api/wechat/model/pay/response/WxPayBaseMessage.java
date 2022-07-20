package pers.cc.spring.api.wechat.model.pay.response;

import lombok.Data;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.pay
 *
 * @author chengce
 * @version 2017-10-25 14:56
 */
@Data
public class WxPayBaseMessage {
  /**
   * SUCCESS/FAIL
   * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
   */
  private String return_code;

  /**
   * 返回信息，如非空，为错误原因
   * 签名失败
   * 参数格式校验错误
   */
  private String return_msg;
}
