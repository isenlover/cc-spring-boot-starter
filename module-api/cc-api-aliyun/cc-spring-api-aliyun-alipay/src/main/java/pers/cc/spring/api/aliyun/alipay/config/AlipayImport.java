package pers.cc.spring.api.aliyun.alipay.config;

import pers.cc.spring.api.aliyun.alipay.annotation.AliPayApp;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2018-06-14 22:41
 */
public class AlipayImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = AliPayApp.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      System.setProperty("AliPayApp", JSON.toJSONString(annotationMetadata.getAnnotationAttributes(annotationName)));
    }
    return new String[]{AlipayConfiguration.class.getName()};
  }
}
