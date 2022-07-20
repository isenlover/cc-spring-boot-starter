package pers.cc.spring.data.jpa.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2021-03-24 12:35
 */
public class JpaImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    return new String[]{JpaConfiguration.class.getName()};
  }
}
