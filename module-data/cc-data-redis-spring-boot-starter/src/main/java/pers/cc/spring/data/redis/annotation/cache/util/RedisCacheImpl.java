package pers.cc.spring.data.redis.annotation.cache.util;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
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
import java.util.stream.Stream;

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
                          String condition) {
    if (saveBlank || !isNull(CommonUtils.isEmpty(result), isMessageNull(result), isOptionalNull(result),
        isPageResultNull(result))) {
      // 如果满足缓存条件则存储
      if (CommonUtils.isNotEmpty(condition)) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(condition);
        EvaluationContext evaluationContext = SpelUtils.getEvaluationContext(condition, joinPoint);
        return Boolean.TRUE.equals(expression.getValue(evaluationContext, Boolean.class));
      } else {
        return true;
      }
    } else {
      return false;
    }
  }

}
