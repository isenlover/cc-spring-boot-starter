package pers.cc.spring.security.jwt.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import pers.cc.spring.security.jwt.annotation.JwtSecurityParam;
import pers.cc.spring.security.jwt.model.DefaultParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengce
 * @version 2018-06-15 01:15
 */
public class JwtSecurityParamImport implements DeferredImportSelector {
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
    String annotationName = JwtSecurityParam.class.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      processJwtSecurityParam(annotationMetadata, annotationName);
    }
    return new String[]{};
  }

  private void processJwtSecurityParam(AnnotationMetadata annotationMetadata, String annotationName) {
    Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(annotationName);
    String[] values = (String[]) Objects.requireNonNull(annotationAttributes).get("value");
    boolean isPermitDefault = (boolean) annotationAttributes.get("permitDefault");
    boolean isPermitSwagger = (boolean) annotationAttributes.get("permitSwagger");
    List<String> permits = new ArrayList<>();
    if (isPermitDefault) {
      permits.addAll(DefaultParam.DEFAULT_PERMIT_REQUEST);
    }
    permits.addAll(Arrays.stream(values).collect(Collectors.toList()));
    if (isPermitSwagger) {
      permits.addAll(DefaultParam.DEFAULT_SWAGGER_PERMIT_REQUEST);
    }
    annotationAttributes.remove("value");
    annotationAttributes.put("permitRequestsText", String.join(";", permits));
    System.setProperty("JwtSecurityParam", JSON.toJSONString(annotationAttributes));
  }
}
