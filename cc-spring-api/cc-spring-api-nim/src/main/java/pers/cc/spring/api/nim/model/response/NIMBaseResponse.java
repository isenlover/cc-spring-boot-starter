package pers.cc.spring.api.nim.model.response;

import lombok.Data;

/**
 * @author chengce
 * @version 2017-03-23 下午12:15
 */
@Data
public class NIMBaseResponse {
  private int code;
  private String info;
}
