package pers.cc.spring.api.aliyun.oss.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.api.aliyun.oss.annotation.AliyunOSSParam;

/**
 * @author chengce
 * @version 2018-06-14 22:41
 */
public class AliyunOSSImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = AliyunOSSParam.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("AliyunOSSParam", JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
    }
    return new String[]{AliyunOSSConfiguration.class.getName()};
  }
}
