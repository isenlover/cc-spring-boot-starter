package pers.cc.spring.api.nim.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.nim.annotation.EnableNim;
import pers.cc.spring.api.nim.annotation.NimApp;
import pers.cc.spring.api.nim.model.NimInformation;
import pers.cc.spring.api.nim.model.response.NIMBaseResponse;
import pers.cc.spring.api.nim.model.team.vo.NIMHistoryBaseVO;
import pers.cc.spring.api.nim.model.team.vo.NIMHistoryContentVO;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.other.MathUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengce
 * @version 2017-03-23 上午11:25
 */
@Service
@ConditionalOnBean(annotation = {EnableNim.class, NimApp.class})
public class NIMHelper {
  @Autowired
  NimInformation nimInformation;

  /**
   * 获取网易云信每次交互必加的header
   *
   * @return header map
   */
  public Map<String, String> getHttpHeaders() {
    String random = MathUtils.getRandom(17, MathUtils.NonceType.CHAR);
    String curTime = String.valueOf(System.currentTimeMillis() / 1000);
    String signature = CheckSumBuilder.getCheckSum(nimInformation.getAppSecret(), random, curTime);

    Map<String, String> headers = new HashMap<>();
    headers.put("AppKey", nimInformation.getAppKey());
    headers.put("Nonce", random);
    headers.put("CurTime", curTime);
    headers.put("CheckSum", signature);
    headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8;");

    return headers;
  }

  /**
   * 针对网易云信的http response处理
   *
   * @param resp  response 字符串
   * @param clazz 类名
   * @return 类
   */
  public <T> Message<T> checkResponse(String resp, Class<T> clazz) {
    Message<T> message = new Message<>();
    NIMBaseResponse baseResponse = JSON.parseObject(resp, NIMBaseResponse.class);
    message.setCode(String.valueOf(baseResponse.getCode()));
    if (baseResponse.getCode() == 200) {
      message.setSuccess(true);
      message.setMessage("网易云信接口调用成功");
      message.setData(JSON.parseObject(baseResponse.getInfo(), clazz));
      return message;
    } else {
      message.setMessage(getNIMResponseError(baseResponse.getCode()));
    }
    return message;
  }

  public <T> Message<List<NIMHistoryContentVO<T>>> getHistoryResponse(String resp, Class<T> clz) {
    NIMHistoryBaseVO<T> baseVO = JSON.parseObject(resp, new TypeReference<NIMHistoryBaseVO<T>>(clz) {
    });
    if (baseVO.getCode() == 200) {
      return Message.ok(baseVO.getMsgs());
    }
    return Message.<List<NIMHistoryContentVO<T>>>failed().message("获取失败").build();
  }

  private String getNIMResponseError(int code) {
    switch (code) {
      //普通code码
      case 201:
        return "客户端版本不对,需升级SDK";
      case 301:
        return "被封禁";
      case 302:
        return "用户名或密码错误";
      case 315:
        return "IP限制";
      case 403:
        return "非法操作或没有权限";
      case 404:
        return "对象不存在";
      case 405:
        return "参数长度过长";
      case 406:
        return "对象只读";
      case 408:
        return "客户端请求超时";
      case 413:
        return "验证失败(短信服务)";
      case 414:
        return "参数错误";
      case 415:
        return "客户端网络问题";
      case 416:
        return "频率控制";
      case 417:
        return "重复操作";
      case 418:
        return "通道不可用(短信服务)";
      case 419:
        return "数量超过上限";
      case 422:
        return "账号被禁用";
      case 431:
        return "HTTP重复请求";
      case 500:
        return "服务器内部错误";
      case 503:
        return "服务器繁忙";
      case 508:
        return "消息撤回时间超限";
      case 509:
        return "无效协议";
      case 514:
        return "服务不可用";
      case 998:
        return "解包错误";
      case 999:
        return "打包错误";
      //群相关错误码
      case 801:
        return "群人数达到上限";
      case 802:
        return "没有权限";
      case 803:
        return "群不存在";
      case 804:
        return "用户不在群";
      case 805:
        return "群类型不匹配";
      case 806:
        return "创建群数量达到限制";
      case 807:
        return "群成员状态错误";
      case 808:
        return "申请成功";
      case 809:
        return "已经在群内";
      case 810:
        return "邀请成功";
      //音视频、白板通话相关错误码
      case 9102:
        return "通道失效";
      case 9103:
        return "已经在他端对这个呼叫响应过了";
      case 11001:
        return "通话不可达，对方离线状态";
      //特定业务相关错误码
      case 10431:
        return "输入email不是邮箱";
      case 10432:
        return "输入mobile不是手机号码";
      case 10433:
        return "注册输入的两次密码不相同";
      case 10434:
        return "企业不存在";
      case 10435:
        return "登陆密码或帐号不对";
      case 10436:
        return "app不存在";
      case 10437:
        return "email已注册";
      case 10438:
        return "手机号已注册";
      case 10441:
        return "app名字已经存在";
      default:
        return "NIM未知错误";
    }
  }
}
