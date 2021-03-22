package pers.cc.spring.security.jwt.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * @author chengce
 * @version 2021-03-08 18:37
 */
public class Test extends AccessDeniedException {
  public Test(String msg) {
    super(msg);
  }
}
