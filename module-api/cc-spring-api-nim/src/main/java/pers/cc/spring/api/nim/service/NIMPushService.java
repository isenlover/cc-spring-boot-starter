package pers.cc.spring.api.nim.service;

import pers.cc.spring.api.nim.exception.NIMPushException;
import pers.cc.spring.api.nim.model.push.NIMCustomPushDTO;
import pers.cc.spring.api.nim.model.push.NIMPushDTO;
import pers.cc.spring.api.nim.model.push.NIMPushResp;
import pers.cc.spring.core.message.Message;

/**
 * com.cc.api.netease.service
 * 推送推送消息接口
 *
 * @author chengce
 * @version 2018-01-08 14:30
 */
@Deprecated
public interface NIMPushService {
  /**
   * 发送批量普通消息
   *
   * @param pushDTO 推送消息
   * @return 结果
   */
  Message<NIMPushResp> sendBatchMessage(NIMPushDTO pushDTO) throws NIMPushException;

  /**
   * 发送批量自定义消息
   *
   * @param pushDTO 推送消息
   * @return 结果
   */
  Message<NIMPushResp> sendBatchMessage(NIMCustomPushDTO pushDTO) throws NIMPushException;


}
