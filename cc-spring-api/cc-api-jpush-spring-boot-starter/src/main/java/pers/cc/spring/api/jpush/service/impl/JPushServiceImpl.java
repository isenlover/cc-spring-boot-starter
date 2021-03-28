package pers.cc.spring.api.jpush.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pers.cc.spring.api.jpush.properties.JPushApiProperties;
import pers.cc.spring.api.jpush.service.JPushService;

/**
 * @author chengce
 * @version 2021-02-09 15:00
 */
@Slf4j
public class JPushServiceImpl implements JPushService {
  @Autowired
  JPushApiProperties jPushApiProperties;

  public static PushPayload buildPushObject_android_and_ios(String content) {
//    Map<String, String> extras = new HashMap<String, String>();
//    extras.put("test", "https://community.jiguang.cn/push");
    JsonObject sound = new JsonObject();
    sound.add("critical", new JsonPrimitive(1));
    sound.add("name", new JsonPrimitive("default"));
    sound.add("volume", new JsonPrimitive(1));
    return PushPayload.newBuilder()
        .setPlatform(Platform.android_ios())
        .setAudience(Audience.newBuilder()
            .addAudienceTarget(AudienceTarget.alias("cc"))
            .build()
        )
//        .setAudience(Audience.all())
        .setNotification(Notification.newBuilder()
            .setAlert(content)
//            .addPlatformNotification(AndroidNotification.newBuilder()
//                .setTitle("Android Title")
//                .addExtras(extras).build())
            .addPlatformNotification(IosNotification.newBuilder()
                .incrBadge(1)
                .setSound(sound)
                .addExtra("extra_key", "extra_value").build())
            .build())
        .build();
  }

  @Override
  public void pushMessage(String message) {
    ClientConfig clientConfig = ClientConfig.getInstance();
    final JPushClient jpushClient = new JPushClient(jPushApiProperties.getSecret(), jPushApiProperties.getAppKey(), null, clientConfig);
//        String authCode = ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET);
    // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
    // Call setHttpClient to set httpClient,
    // If you don't invoke this method, default httpClient will use NativeHttpClient.

//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
//        NettyHttpClient httpClient =new NettyHttpClient(authCode, null, clientConfig);
//        jpushClient.getPushClient().setHttpClient(httpClient);
    final PushPayload payload = buildPushObject_android_and_ios(message);
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_all_alias_alert();
    try {
      PushResult result = jpushClient.sendPush(payload);
      log.info("JPush result - " + result);
      // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
      // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
      // jpushClient.close();
    } catch (APIConnectionException e) {
      log.error("Connection error. Should retry later. ");
      log.error("Sendno: " + payload.getSendno());

    } catch (APIRequestException e) {
      log.error("Error response from JPush server. Should review and fix it. ");
      log.info("HTTP Status: " + e.getStatus());
      log.info("Error Code: " + e.getErrorCode());
      log.info("Error Message: " + e.getErrorMessage());
      log.info("Msg ID: " + e.getMsgId());
      log.error("Sendno: " + payload.getSendno());
    }
  }
}
