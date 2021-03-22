package pers.cc.spring.core.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pers.cc.spring.core.util.other.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chengce
 * @version 2019-01-03 00:51
 */
@Slf4j
public class SpelUtils {

  private final static LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

  public static EvaluationContext getEvaluationContext(String[] keys, JoinPoint joinPoint) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    // 尝试获取方法参数名
    String[] params;
    EvaluationContext context = new StandardEvaluationContext();
    Object[] args = joinPoint.getArgs();
    List<String> paramList = new ArrayList<>();
    // 判断参数是否为p开头
    for (String key : keys) {
      if (key.contains("#p")) {
        for (int i = 0; i < methodSignature.getMethod().getParameters().length; i++) {
          paramList.add("p" + i);
        }
        break;
      }
    }
    if (paramList.size() == 0) {
      // 如果没有获取到，则判断是否有@ParamName注解
      Annotation[][] annotationList = methodSignature.getMethod().getParameterAnnotations();
      Class paramNameClass;
      try {
        paramNameClass = Class.forName("pers.cc.spring.data.redis.annotation.cache.ParamName");
        for (Annotation[] annotations : annotationList) {
          for (Annotation annotation : annotations) {
            if (annotation.getClass().isInstance(paramNameClass)) {
              paramList.add(ClassUtils.getValue(annotation, "value"));
            }
          }
        }
      } catch (ClassNotFoundException e) {
        log.error("SpelUtils getEvaluationContext 查找ParamName 出错");
        e.printStackTrace();
      }

    }
    if (paramList.size() == 0) {
      // 最后取方法参数
      params = localVariableTableParameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
    } else {
      params = paramList.toArray(new String[0]);
    }
    if (CommonUtils.isNotEmpty(params)) {
      for (int len = 0; len < params.length; len++) {
        context.setVariable(params[len], args[len]);
      }
    }
    return context;
  }

  public static EvaluationContext getEvaluationContext(String key, JoinPoint joinPoint) {
    return getEvaluationContext(new String[]{key}, joinPoint);
  }

  public static String getSpELValue(String spEL, JoinPoint joinPoint) {
    ExpressionParser expressionParser = new SpelExpressionParser();
    EvaluationContext evaluationContext = SpelUtils.getEvaluationContext(spEL, joinPoint);
    Expression keyExpression = expressionParser.parseExpression(spEL);
    return keyExpression.getValue(evaluationContext, String.class);
  }
}
