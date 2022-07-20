package pers.cc.spring.api.nim.config;

import com.alibaba.fastjson.JSON;
import pers.cc.spring.api.nim.annotation.NimApp;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2018-06-01 14:47
 * @fixed 2021-03-18
 */
public class NimConfigurationImport implements DeferredImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = NimApp.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("NimApp", JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
      return new String[]{NimConfiguration.class.getName()};
    } else {
      return new String[1];
    }
  }
}
