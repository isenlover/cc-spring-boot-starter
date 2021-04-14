package pers.cc.spring.core.annotation.swagger.extension.core;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.annotation.swagger.extension.annotation.ApiGroup;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Set;

/**
 * 接口Response返回结果 关于组的构建
 */
@Component
@Order()
@ConditionalOnBean(annotation = EnableSwagger2.class)
public class OperationResponseClassGroupReader implements OperationBuilderPlugin {

  @Override
  public void apply(OperationContext context) {
    ResolvedType returnType = context.getReturnType();
    returnType = context.alternateFor(returnType);

    Optional<ApiGroup> annotation = context.findAnnotation(ApiGroup.class);
    if (annotation.isPresent()) {
      /*当前接口存在ApiGroup注解*/
      Class<?> clazz = annotation.get().value();
      //获取组名称
      String groupName = StringUtils.substringAfterLast(clazz.getName(), ".");

      /*获得旧的ModelRef*/
      Operation operation = context.operationBuilder().build();
      ModelReference oldModelRef = operation.getResponseModel();

      //旧的ModelRef.type拼接上组名称成newTypeName
      String newTypeName = oldModelRef.getType() + groupName;

      //用newTypeName构建ModelRef
      ModelReference newModelRef = new ModelRef(newTypeName);

      /*用新的ModelRef替换旧的ModelRef(接口返回的结果会用newTypeName作为key到Map里面去查找页面上展示用的返回实体参数)*/
      context.operationBuilder().responseModel(newModelRef);
      Set<ResponseMessage> responseMessages = operation.getResponseMessages();
      responseMessages.forEach(responseMessage -> {
        if (responseMessage.getCode() == 200) {
          responseMessage = new ResponseMessageBuilder()
              .code(responseMessage.getCode())
              .message(responseMessage.getMessage())
              .responseModel(newModelRef)
              .build();
          context.operationBuilder().responseMessages(Sets.newHashSet(responseMessage));
        }
      });
    }
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }
}
