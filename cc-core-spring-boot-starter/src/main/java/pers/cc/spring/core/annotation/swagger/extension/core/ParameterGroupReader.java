package pers.cc.spring.core.annotation.swagger.extension.core;


import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.annotation.swagger.extension.annotation.ApiGroup;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 接口请求参数Model 关于组的构建(能处理@RequestBody的,没有任何注解的query参数无法识别)
 */
@Component
@Order()
@ConditionalOnBean(annotation = EnableSwagger2.class)
public class ParameterGroupReader implements ParameterBuilderPlugin {

  @Override
  public void apply(ParameterContext context) {
    ResolvedMethodParameter methodParameter = context.resolvedMethodParameter();
    ResolvedType parameterType = methodParameter.getParameterType();
    Optional<ApiGroup> annotation = methodParameter.findAnnotation(ApiGroup.class);
    if (annotation.isPresent()) {
      /*参数是否存在 ApiGroup注解*/
      Class<?> clazz = annotation.get().value();
      String groupName = StringUtils.substringAfterLast(clazz.getName(), ".");
      ModelReference oldModelRef = context.parameterBuilder().build().getModelRef();
      context.parameterBuilder().modelRef(new ModelGroupReferenceProvider(oldModelRef, groupName).apply(parameterType));
    }
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }
}
