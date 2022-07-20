package pers.cc.spring.security.jwt.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.security.jwt.service.impl.JwtServiceImpl;

/**
 * @author chengce
 * @version 2018-06-15 01:15
 */
public class JwtSecurityConfigurationImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    return new String[]{
        JwtParamDefaultConfiguration.class.getName(),
        JwtSecurityConfigurerAdapter.class.getName(),
        JwtServiceImpl.class.getName(),
    };
  }
}
