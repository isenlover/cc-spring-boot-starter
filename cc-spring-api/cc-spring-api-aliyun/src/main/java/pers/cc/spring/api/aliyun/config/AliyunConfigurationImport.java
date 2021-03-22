package pers.cc.spring.api.aliyun.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.api.aliyun.annotation.AliyunApp;
import pers.cc.spring.api.aliyun.oss.annotation.AliyunOSSApp;
import pers.cc.spring.api.aliyun.oss.service.impl.AliyunOssImpl;
import pers.cc.spring.api.aliyun.property.AliyunProperties;
import pers.cc.spring.api.aliyun.sms.annotation.AliyunSMSApp;
import pers.cc.spring.api.aliyun.sms.service.impl.AliyunSMSImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个返回值是注册bean用的
 * @author chengce
 * @version 2018-06-14 22:41
 */
public class AliyunConfigurationImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    List<String> classList = new ArrayList<>();
    classList.add(AliyunProperties.class.getName());
    String annotationName = AliyunSMSApp.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("aliyunSMSApp",
          JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
      classList.add(AliyunSMSConfiguration.class.getName());
//      return new String[]{AliyunSMSConfiguration.class.getName()};
    }
    annotationName = AliyunApp.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("aliyunApp",
          JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
      classList.add(AliyunConfiguration.class.getName());
      classList.add(AliyunSMSImpl.class.getName());
//      return new String[]{AliyunConfiguration.class.getName()};
    }
    annotationName = AliyunOSSApp.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("aliyunOSSApp",
          JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
      classList.add(AliyunOSSConfiguration.class.getName());
      classList.add(AliyunOssImpl.class.getName());
//      return new String[]{AliyunOSSConfiguration.class.getName()};
    }
    if (classList.size() == 0) {
      return new String[1];
    }
    return classList.toArray(new String[]{});
  }
}
