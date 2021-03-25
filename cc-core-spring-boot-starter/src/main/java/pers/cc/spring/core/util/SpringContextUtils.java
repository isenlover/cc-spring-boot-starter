package pers.cc.spring.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chengce
 * @version 2021-03-25 13:37
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextUtils.applicationContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static <T> T getBean(String beanId) throws BeansException {
    return (T) applicationContext.getBean(beanId);
  }

  public static <T> T getBean(Class<T> cla) throws BeansException {
    return applicationContext.getBean(cla);
  }
}
