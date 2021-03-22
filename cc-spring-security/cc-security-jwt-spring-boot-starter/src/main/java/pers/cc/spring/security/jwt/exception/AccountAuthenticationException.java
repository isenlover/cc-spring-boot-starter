package pers.cc.spring.security.jwt.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.message.MessageCode;

/**
 * @author chengce
 * @version 2021-03-08 17:19
 */
public class AccountAuthenticationException extends AuthenticationException {

  public AccountAuthenticationException(String msg) {
    super(msg);
  }

  public AccountAuthenticationException(MessageCode messageCode) {
    super(JSON.toJSONString(Message.failed().messageCode(messageCode).build()));
  }
}
