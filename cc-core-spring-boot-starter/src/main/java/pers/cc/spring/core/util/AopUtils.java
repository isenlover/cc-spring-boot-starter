package pers.cc.spring.core.util;

import org.springframework.aop.framework.AopContext;

/**
 * @author chengce
 * @version 2019-01-03 18:27
 */
public class AopUtils {

  public static <T> T getCurrentProxy() {
    return (T) AopContext.currentProxy();
  }
}
