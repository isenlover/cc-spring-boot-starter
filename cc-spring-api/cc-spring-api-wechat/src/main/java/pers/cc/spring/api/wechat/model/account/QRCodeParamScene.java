package pers.cc.spring.api.wechat.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.cc.api.wechat.pers.cc.cfootball.common.model.account
 *
 * @author chengce
 * @version 2017-10-24 21:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeParamScene {

  private Long scene_id;

  private String scene_str;
}
