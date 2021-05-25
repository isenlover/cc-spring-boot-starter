package pers.cc.spring.api.wechat.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.cc.spring.api.wechat.annotation.EnableWechatBasicScheduleTask;
import pers.cc.spring.api.wechat.service.WechatCoreService;
import pers.cc.spring.api.wechat.service.WechatJsService;

/**
 * com.cc.api.wechat.service.impl
 * 微信定时任务，用于获取token
 *
 * @author chengce
 * @version 2017-10-24 16:05
 */
@Slf4j
@Component
@ConditionalOnBean(annotation = EnableWechatBasicScheduleTask.class)
public class WechatBasicScheduled {

  @Autowired
  WechatCoreService baseService;

  @Autowired
  WechatJsService wechatJsService;

  /**
   * 每10分钟执行一次
   */
//    @Scheduled(cron = "0 0 */1 * * ?")
  @Scheduled(fixedDelay = 600000)
//    @Scheduled(cron = "0 0/30 * * * ?")
  public void executeUploadTaskForToken() {
    baseService.initAccessToken();
    wechatJsService.getJSTicket();
  }
}
