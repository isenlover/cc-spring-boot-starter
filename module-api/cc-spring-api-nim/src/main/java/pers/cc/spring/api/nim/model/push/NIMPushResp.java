package pers.cc.spring.api.nim.model.push;

import pers.cc.spring.api.nim.model.response.NIMBaseResponse;
import lombok.Data;

import java.util.List;

/**
 * com.cc.api.netease.model.push
 *
 * @author chengce
 * @version 2018-01-08 14:43
 */
@Data
public class NIMPushResp extends NIMBaseResponse {
  private List<String> unregister;

  public List<String> getUnregister() {
    return unregister;
  }

  public void setUnregister(List<String> unregister) {
    this.unregister = unregister;
  }
}
