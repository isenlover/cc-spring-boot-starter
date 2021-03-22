package pers.cc.spring.core.annotation.simple.task.monitor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.MathUtils;

/**
 * 简单计时器实现
 *
 * @author chengce
 * @version 2018-04-25 14:50
 * @see SimpleTaskMonitor
 */
@Component
@Slf4j
@Aspect
public class SimpleTaskMonitoryAspect {

  @Around(value = "execution(* *(..)) && @annotation(simpleTimer)")
  public Object writeLog(ProceedingJoinPoint proceedingJoinPoint, SimpleTaskMonitor simpleTimer) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = proceedingJoinPoint.proceed();
    String suffix = "";
    if (CommonUtils.isNotEmpty(simpleTimer.suffix())) {
      suffix = CommonUtils.getCacheKey(simpleTimer.suffix(), proceedingJoinPoint);
    }
    long end = System.currentTimeMillis() - start;
    switch (simpleTimer.timeUnit()) {
      default:
        break;
      case SECONDS:
        end = end / 1000;
        break;
      case MINUTES:
        end = end / 1000 / 60;
        break;
      case HOURS:
        end = end / 1000 / 60 / 60;
        break;
      case DAYS:
        end = end / 1000 / 60 / 24;
        break;
    }
    log.info(proceedingJoinPoint.getSignature().getName() + "任务耗时: " + end + suffix);
    return result;
  }
}
