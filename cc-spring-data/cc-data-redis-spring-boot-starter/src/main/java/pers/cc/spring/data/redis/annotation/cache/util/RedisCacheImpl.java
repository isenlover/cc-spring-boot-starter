package pers.cc.spring.data.redis.annotation.cache.util;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.page.PageResults;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.SpelUtils;
import pers.cc.spring.data.redis.annotation.cache.RedisOptional;
import pers.cc.spring.data.redis.service.RedisService;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chengce
 * @version 2018-05-30 00:23
 * @see pers.cc.spring.data.redis.annotation.cache.RedisCacheAspect
 */
@Service
public class RedisCacheImpl implements RedisCacheService {
  @Autowired
  RedisService redisService;

  public final static LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();


//    private EvaluationContext getEvaluationContext(String[] keys, JoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        // 尝试获取方法参数名
//        String[] params;
//        EvaluationContext context = new StandardEvaluationContext();
//        Object[] args = joinPoint.getArgs();
//        List<String> paramList = new ArrayList<>();
//        // 判断参数是否为p开头
//        for (String key : keys) {
//            if (key.contains("#p")) {
//                for (int i = 0; i < methodSignature.getMethod().getParameters().length; i++) {
//                    paramList.add("p" + i);
//                }
//                break;
//            }
//        }
//        if (paramList.size() == 0) {
//            // 如果没有获取到，则判断是否有@ParamName注解
//            Annotation[][] annotationList = methodSignature.getMethod().getParameterAnnotations();
//            for (Annotation[] annotations : annotationList) {
//                for (Annotation annotation : annotations) {
//                    if (annotation instanceof ParamName) {
//                        paramList.add(((ParamName) annotation).value());
//                    }
//                }
//            }
//        }
//        if (paramList.size() == 0) {
//            // 最后取方法参数
//            params = localVariableTableParameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
//        } else {
//            params = paramList.toArray(new String[0]);
//        }
//        if (CommonUtils.isNotEmpty(params)) {
//            for (int len = 0; len < params.length; len++) {
//                context.setVariable(params[len], args[len]);
//            }
//        }
//        return context;
//    }

  @Override
  public void cache(String key, Object result, boolean forever, long time, TimeUnit timeUnit) {
    Object cacheObject = result;
    if (result instanceof Optional) {
      Optional optional = (Optional) result;
      cacheObject = RedisOptional.of(optional.orElse(null));
    }
    if (forever) {
      redisService.cacheValue(key, cacheObject, true, 36135, TimeUnit.DAYS);
    } else {
      redisService.cacheValue(key, cacheObject, true, time, timeUnit);
    }
  }

  private boolean isNull(Boolean... booleans) {
    return Arrays.stream(booleans).filter(aBoolean -> aBoolean).findAny().orElse(false);
  }

  private boolean isPageResultNull(Object result) {
    if (result instanceof PageResults) {
      return ((PageResults) result).getResults() == null;
    }
    return false;
  }

  private boolean isOptionalNull(Object result) {
    if (result instanceof Optional) {
      return !((Optional) result).isPresent();
    }
    return false;
  }

  private boolean isMessageNull(Object result) {
    if (result instanceof Message) {
      return isNull(isPageResultNull(((Message) result).getData()), isOptionalNull(((Message) result).getData()));
    }
    return false;
  }

  @Override
  public boolean canCache(JoinPoint joinPoint,
                          Object result,
                          boolean saveBlank,
                          String condition,
                          String key) {
    if (saveBlank || !isNull(CommonUtils.isEmpty(result), isMessageNull(result), isOptionalNull(result),
        isPageResultNull(result))) {
      // 如果满足缓存条件则存储
      if (CommonUtils.isNotEmpty(condition)) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(condition);
        return expression.getValue(
            SpelUtils.getEvaluationContext(key, joinPoint),
            Boolean.class);
      } else {
        return true;
      }
    } else {
      return false;
    }
  }
}
