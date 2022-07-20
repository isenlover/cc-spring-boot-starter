package pers.cc.spring.api.aliyun.common.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.api.aliyun.common.annotation.AliyunParam;

/**
 * @author chengce
 * @version 2018-06-14 22:41
 */
public class AliyunImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = AliyunParam.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("AliyunParam", JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
    }
    return new String[]{AliyunConfiguration.class.getName()};
  }
}
