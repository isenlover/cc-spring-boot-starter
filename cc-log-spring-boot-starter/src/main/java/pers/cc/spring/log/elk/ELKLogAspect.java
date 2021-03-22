package pers.cc.spring.log.elk;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.util.SpelUtils;
import pers.cc.spring.log.elk.enums.ELKLogOperation;
import pers.cc.spring.log.elk.enums.ELKLogStatus;
import pers.cc.spring.log.elk.enums.ELKLogType;
import pers.cc.spring.log.elk.model.ELKLogBuilder;
import pers.cc.spring.log.elk.model.Log;
import pers.cc.spring.log.elk.properties.ELKLogProperties;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志写入切面具体实现类
 * 通过此类实现日志切面写入
 * 需要logback支持
 *
 * @author chengce
 * @version 2018-04-30 16:30
 */
@Slf4j
@Aspect
@Component
public class ELKLogAspect {
  @Autowired
  ELKLogBuilder elkLogBuilder;

  @Autowired
  ELKLogProperties elkLogProperties;

  /**
   * 创建log信息bean
   *
   * @param joinPoint 切入点
   * @param elkLog    注解
   * @return LogBean信息实例
   */
  private Log.Builder getLog(JoinPoint joinPoint, ELKLog elkLog, ELKLogType elkLogType) {
    String elkLogOperation = getSpELValue(elkLog.operation(), joinPoint);
    if (elkLogOperation.equals(ELKLogOperation.AUTO.name())) {
      Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
      if (method.getAnnotation(PostMapping.class) != null) {
        elkLogOperation = ELKLogOperation.INSERT.name();
      } else if (method.getAnnotation(DeleteMapping.class) != null) {
        elkLogOperation = ELKLogOperation.DELETE.name();
      } else if (method.getAnnotation(PutMapping.class) != null) {
        elkLogOperation = ELKLogOperation.UPDATE.name();
      } else if (method.getAnnotation(GetMapping.class) != null) {
        elkLogOperation = ELKLogOperation.SELECT.name();
      }
    }
    return elkLogBuilder.build(joinPoint, elkLogType)
        .operation(elkLogOperation)
        .description(getSpELValue(elkLog.description(), joinPoint));
  }

  private String getSpELValue(String spEL, JoinPoint joinPoint) {
    ExpressionParser expressionParser = new SpelExpressionParser();
    EvaluationContext evaluationContext = SpelUtils.getEvaluationContext(spEL, joinPoint);
    Expression keyExpression = expressionParser.parseExpression(spEL);
    return keyExpression.getValue(evaluationContext, String.class);
  }

  @Around(value = "execution(* *(..)) && @annotation(elkLog)")
  public Object writeLog(ProceedingJoinPoint proceedingJoinPoint, ELKLog elkLog) throws Throwable {
    long start = 0;
    Log.Builder builder = getLog(proceedingJoinPoint, elkLog, ELKLogType.NONE_EXCEPTION).status(
        ELKLogStatus.BEFORE_EXECUTE);
    if (elkLog.taskTime()) {
      start = System.currentTimeMillis();
    }
    if (elkLog.before()) {
      log.info(JSON.toJSONString(builder.build()));
    }
    Object result = proceedingJoinPoint.proceed();
    builder.status(ELKLogStatus.SUCCESS);
    if (result != null) {
      builder.returns(result.toString());
      if (elkLog.result()) {
        builder.description(getSpELValue(elkLog.description(), proceedingJoinPoint) + "，结果：" + result);
      }
    }
    if (elkLog.taskTime()) {
      builder.taskTime(System.currentTimeMillis() - start);
    }
    log.info(JSON.toJSONString(builder.build()));
    return result;
  }

//    /**
//     * 此方法生效于方法执行前
//     *
//     * @param joinPoint 接入点
//     * @param elkLog    注解
//     */
//    @Before(value = "execution(* *(..)) && @annotation(elkLog)")
//    public void writeLog(JoinPoint joinPoint, ELKLog elkLog) {
//        if (elkLog.before()) {
//            log.info(JSON.toJSONString(getLog(joinPoint, elkLog)
//                    .status(ELKLogStatus.BEFORE_EXECUTE)
//                    .build()));
//        }
//    }
//
//    /**
//     * 此方法生效于方法正常执行结束
//     *
//     * @param joinPoint 接入点
//     * @param result    如果方法有返回结果，则输出
//     * @param elkLog    注解
//     */
//    @AfterReturning(value = "execution(* *(..)) && @annotation(elkLog)", returning = "result")
//    public void writeLog(JoinPoint joinPoint, Object result, ELKLog elkLog) {
//        Log.Builder builder = getLog(joinPoint, elkLog).status(ELKLogStatus.SUCCESS);
//        if (result != null) {
//            builder.returns(result.toString());
//        }
//        log.info(JSON.toJSONString(builder.build()));
//    }

  /**
   * 此方法生效于方法抛出异常终止
   *
   * @param joinPoint 接入点
   * @param throwable 异常
   * @param elkLog    注解
   */
  @AfterThrowing(value = "execution(* *(..)) && @annotation(elkLog)", throwing = "throwable")
  public void writeLog(JoinPoint joinPoint, Throwable throwable, ELKLog elkLog) {
    Log.Builder builder = getLog(joinPoint, elkLog, ELKLogType.EXCEPTION).status(ELKLogStatus.FAILURE);
    if (throwable != null) {
      if (throwable instanceof BaseRuntimeException) {
        builder.type(ELKLogType.BASE_RUNTIME_EXCEPTION);
      } else if (throwable instanceof RuntimeException) {
        builder.type(ELKLogType.RUNTIME_EXCEPTION);
      }
      builder.stackTrace(Arrays.toString(throwable.getStackTrace()));
      builder.exception(throwable.toString());
    }
    log.error(JSON.toJSONString(builder.build()));
  }
//    /**
//     * 此方法生效于方法抛出异常终止
//     *
//     * @param joinPoint 接入点
//     * @param exception 异常
//     * @param log       注解
//     */
//    @AfterThrowing(value = "execution(* *(..)) && @annotation(log)", throwing = "exception")
//    public void writeLog(JoinPoint joinPoint, BaseException exception, Log log) {
//        LogAspect.log.error(JSON.toJSONString(getLog(joinPoint, log)
//                .exception(exception.toString())
//                .type(log.logType() == ELKLogStatus.DEFAULT ? ELKLogStatus.ERROR : log.logType())
//                .build()));
//    }
}
