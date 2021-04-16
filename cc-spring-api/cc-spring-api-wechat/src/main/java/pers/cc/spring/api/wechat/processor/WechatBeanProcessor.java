package pers.cc.spring.api.wechat.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import pers.cc.spring.api.wechat.annotation.WechatApp;
import pers.cc.spring.api.wechat.bean.WechatInformation;

/**
 * @author chengce
 * @version 2018-06-01 14:37
 * @see pers.cc.spring.api.wechat.config.WechatConfigurationImport
 */
@Deprecated
//@Service
public class WechatBeanProcessor implements BeanPostProcessor {
  @Autowired
  WechatInformation wxAppBean;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().getAnnotation(WechatApp.class) != null) {
      WechatApp wxApp = bean.getClass().getAnnotation(WechatApp.class);
      if (wxApp != null) {
        wxAppBean.setAppId(wxApp.appId());
        wxAppBean.setAppSecret(wxApp.appSecret());
        wxAppBean.setMerchantId(wxApp.merchantId());
        wxAppBean.setMerchantKey(wxApp.merchantKey());
        wxAppBean.setToken(wxApp.token());
      }
    }
    return bean;
  }
}
