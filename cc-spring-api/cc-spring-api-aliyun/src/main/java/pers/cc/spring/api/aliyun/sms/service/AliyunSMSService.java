package pers.cc.spring.api.aliyun.sms.service;

import com.aliyuncs.exceptions.ClientException;
import pers.cc.spring.api.aliyun.sms.model.AliyunSMS;
import pers.cc.spring.core.message.Message;

/**
 * com.cc.aliyun.sms.service
 *
 * @author chengce
 * @version 2017-10-16 20:59
 */
public interface AliyunSMSService {

  /**
   * 发送短信
   *
   * @param aliyunSMS 短信
   * @param <T>       泛型
   * @param tClass    转化类
   * @return 结果
   * @throws ClientException 异常
   */
  <T> T sendSMS(AliyunSMS aliyunSMS, Class<T> tClass) throws ClientException;

  /**
   * 发送短信
   *
   * @param aliyunSMS 短信
   * @param <T>       泛型
   * @return 结果
   * @throws ClientException 异常
   */
  <T> T sendSMS(AliyunSMS aliyunSMS) throws ClientException;

  /**
   * 带缓存的短信验证码
   *
   * @param mobile 唯一标识，手机号
   * @return 短信实例
   * @throws ClientException 异常
   */
  Message<String> sendSMSCode(String mobile) throws ClientException;

  /**
   * 注销此手机号的验证码
   *
   * @param mobile 手机号
   * @return 注销结果
   */
  boolean removeCode(String mobile);


  /**
   * 检查验证码是否存在、有效
   * 必须应用缓存发送
   *
   * @param mobile 唯一标识，手机号
   * @param code   验证码（前端传来）
   * @return true-验证成功
   */
  boolean checkCode(String mobile, String code);

  /**
   * 检查验证码是否存在、有效
   * 必须应用缓存发送
   *
   * @param mobile 唯一标识，手机号
   * @param code   验证码（前端传来）
   * @param logoff 是否验证成功就注销验证码
   * @return true-验证成功
   */
  boolean checkCode(String mobile, String code, boolean logoff);
}
