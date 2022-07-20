package pers.cc.spring.api.wechat.model.other;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by CC on 2016-04-28 下午5:45
 */
@Data
@NoArgsConstructor
public class AccessTokenBo extends WxBaseResponse implements Serializable {

  /**
   * Token值
   */
  private String access_token;

  /**
   * Token有效期
   */
  private int expires_in;

}
