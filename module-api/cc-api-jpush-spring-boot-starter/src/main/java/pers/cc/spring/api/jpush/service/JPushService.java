package pers.cc.spring.api.jpush.service;

import java.util.List;

/**
 * @author chengce
 * @version 2021-02-09 15:00
 */
public interface JPushService {

  @Deprecated
  void pushMessage(String message);

  /**
   * @param jPushIds ->List alias
   *
   */
  void pushMessage(String message, List<String> jPushIds);

  @Deprecated
  void pushMessage(String message, boolean volume);
}
