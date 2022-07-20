package pers.cc.spring.api.nim.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网易云信用户
 *
 * @author chengce
 * @version 2017-02-23 下午6:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NIMUser {
  /**
   * 云信ID，最大长度32字符，必须保证一个
   * APP内唯一（只允许字母、数字、半角下划线_、@、半角点以及半角-组成，不区分大小写， 会统一小写处理，
   * 请注意以此接口返回结果中的accid为准）
   */
  private String accid;
  /**
   * 云信ID昵称，最大长度64字符，用来PUSH推送时显示的昵称
   */
  private String name;
  /**
   * json属性，第三方可选填，最大长度1024字符
   */
  private String props;
  /**
   * 云信ID头像URL，第三方可选填，最大长度1024
   */
  private String icon;
  /**
   * 云信ID可以指定登录token值，最大长度128字符，并更新，如果未指定，会自动生成token，并在创建成功后返回
   */
  private String token;
}
