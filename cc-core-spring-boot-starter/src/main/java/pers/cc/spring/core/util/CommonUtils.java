package pers.cc.spring.core.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import pers.cc.spring.core.util.other.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * 通用工具类
 *
 * @author chengce
 * @version 2016-07-29 18:41
 */
@Slf4j
public class CommonUtils {
//  public CommonUtils() {
//  }
//
//  /**
//   * 用于JSONInclude 注解的Filter
//   * 永久不序列化
//   */
//  public class AlwaysNotJsonInclude {
//    public AlwaysNotJsonInclude() {
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//      return false;
//    }
//  }

  /**
   * 判断传入的对象是否为空
   * 列表长度为0也会返回true
   *
   * @param objects 对象
   * @return 是否为空
   */
  public static boolean isEmpty(@Nullable Object... objects) {
    if (objects == null) return true;
    for (Object o : objects) {
      if (o != null) {
        if (o instanceof List && ((List) o).size() == 0) {
          return true;
        } else if (o.equals("")) {
          return true;
        }
      } else {
        return true;
      }

    }
    return false;
  }

  /**
   * @see CommonUtils#isEmpty(Object...)
   */
  public static boolean isNotEmpty(@Nullable Object... objects) {
    return !isEmpty(objects);
  }


  /**
   * 执行js,并从中取值
   *
   * @param js  js code
   * @param key 键值
   * @return 值
   * @throws ScriptException 异常
   */
  public static Object getJSValue(String js, String key) throws ScriptException {
    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");
    scriptEngine.eval(js);
    return scriptEngine.get(key);
  }

  /**
   * 代理
   */
  public static void initFiddler() {
    System.setProperty("http.proxyHost", "localhost");
    System.setProperty("http.proxyPort", "8888");
    System.setProperty("https.proxyHost", "localhost");
    System.setProperty("https.proxyPort", "8888");
  }

  /**
   * json 字符串转object，如果非法则返回null
   *
   * @param json
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T jsonToObject(String json, Class<T> clazz) {
    try {
      return JSON.parseObject(json, clazz);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 保留几位
   *
   * @param value
   * @param position
   * @return
   */
  public static double getDouble(double value, int position) {
    BigDecimal bd = new BigDecimal(value);
    return bd.setScale(position, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 是否为基本类型
   *
   * @param object
   * @return
   */
  public static boolean isBaseType(Object object) {
    return object instanceof Integer || object instanceof String
        || object instanceof Boolean || object instanceof Long || object instanceof Double || object instanceof Float;
  }

  public static <T> CompletionService<T> getCompletionService(int threads) {
    ExecutorService executorService = Executors.newFixedThreadPool(threads);
    return new ExecutorCompletionService<>(executorService);
  }

  public static String getCacheKey(String key, JoinPoint joinPoint) {
    return getCacheKeys(new String[]{key}, joinPoint).get(0);
  }

  public static List<String> getCacheKeys(String[] keys, JoinPoint joinPoint) {
    ExpressionParser expressionParser = new SpelExpressionParser();
    List<String> realKeys = new ArrayList<>();
    if (CommonUtils.isNotEmpty(keys)) {
      EvaluationContext evaluationContext = SpelUtils.getEvaluationContext(keys, joinPoint);
      for (String key : keys) {
//                if (key.contains("'")) {
        Expression keyExpression = expressionParser.parseExpression(key);
        realKeys.add(keyExpression.getValue(evaluationContext, String.class));
//                } else {
//                    throw new RedisRuntimeException(MessageCode.SERVER_ERROR_REDIS_KEY_EXCEPTION);
//                }
      }
      return realKeys;
    } else {
//            // 类名
      String classFullName = joinPoint.getTarget().toString();
      String className = classFullName.substring(classFullName.lastIndexOf(".") + 1, classFullName.indexOf("@"));
      Signature signature = joinPoint.getSignature();
      MethodSignature methodSignature = (MethodSignature) signature;
      // 方法名
      String methodName = methodSignature.getName();
      // 方法参数值
      Object[] paramValues = joinPoint.getArgs();
      // 组装方法和参数
      // 检查值，减少key长度
      StringBuilder paramValuesBuilder = new StringBuilder();
      for (Object paramValue : paramValues) {
        if (CommonUtils.isBaseType(paramValue)) {
          paramValuesBuilder.append(paramValue).append(",");
        } else if (paramValue instanceof Date) {
          paramValuesBuilder.append(((Date) paramValue).getTime()).append(",");
        } else if (paramValue != null) {
          paramValuesBuilder.append(paramValue.toString()).append(",");
        } else {
          paramValuesBuilder.append("null").append(",");
        }
      }
      String key = className + "#" + methodName + "(" + StringUtils.removeLastCharacter(paramValuesBuilder.toString())
          .replace("[", "")
          .replace("]", "") + ")";
//            return Arrays.asList(methodSignature.getMethod().getName());
      return Collections.singletonList(key);
    }
  }
}
