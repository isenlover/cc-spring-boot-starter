package pers.cc.spring.api.wechat.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.api.wechat.annotation.WechatApp;

/**
 * @author chengce
 * @version 2018-06-01 14:47
 * @fixed 2021-03-18
 */
public class WechatConfigurationImport implements DeferredImportSelector {

//    private ClassLoader classLoader;

//    @Autowired
//    WechatAppBean wechatAppBean;

//    @Override
//    public void setBeanClassLoader(ClassLoader classLoader) {
//        this.classLoader = classLoader;
//    }

  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = WechatApp.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("wechatApp", JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
      return new String[]{WechatConfiguration.class.getName()};
    } else {
      return new String[1];
    }
  }
}
