package pers.cc.spring.core.annotation.simple.task.monitor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 简单的计时器
 * 计算任务一共用时多少
 *
 * @author chengce
 * @version 2018-04-25 14:49
 * @see SimpleTaskMonitoryAspect
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SimpleTaskMonitor {

  TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

  String suffix() default "'毫秒'";
}
