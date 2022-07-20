package pers.cc.spring.api.wechat.model.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Created by CC on 2016-05-12 下午10:37
 * 微信错误代码
 * @fixed 2021-03-18
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WxBaseResponse {
  /**
   * 错误代码
   */
  protected int errcode;

  /**
   * 错误信息
   */
  protected String errmsg;
}
