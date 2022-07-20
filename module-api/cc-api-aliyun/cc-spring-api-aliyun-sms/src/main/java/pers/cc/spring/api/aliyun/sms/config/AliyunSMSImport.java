package pers.cc.spring.api.aliyun.sms.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.api.aliyun.sms.annotation.AliyunSMSParam;

/**
 * 这个返回值是注册bean用的
 * @author chengce
 * @version 2018-06-14 22:41
 */
public class AliyunSMSImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = AliyunSMSParam.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("AliyunSMSParam", JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
    }
    return new String[]{AliyunSMSConfiguration.class.getName()};
  }
}
