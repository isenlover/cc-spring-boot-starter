package pers.cc.spring.api.aliyun.alipay.bean;

import com.alipay.api.domain.AlipayTradePagePayModel;
import lombok.Data;

/**
 * @author chengce
 * @version 2021-04-20 13:08
 */
@Data
public class PayModel {

  private AlipayTradePagePayModel pagePayModel;

  private String returnUrl;

  private String notifyUrl;
}
