package pers.cc.spring.log.elk;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import pers.cc.spring.log.elk.enums.ELKLogOperation;

import java.lang.annotation.*;

/**
 * ELK日志系统
 * 日志依赖Logback配置
 * 最终呈现于Kibana可视化界面
 *
 * @author chengce
 * @version 2018-04-30 16:24
 * @see ELKLogAspect
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface ELKLog {
    /**
     * Optional
     * <p>
     * 操作，
     * 如果Log位于controller上，会默认进行判断
     * post->{@link ELKLogOperation#INSERT}
     * put->{@link ELKLogOperation#UPDATE}
     * delete->{@link ELKLogOperation#DELETE}
     * get->{@link ELKLogOperation#SELECT}
     */
    String operation() default "'AUTO'";

    /**
     * Optional
     * <p>
     * 进入方法前是否写入日志
     */
    boolean before() default false;

    /**
     * 是否记录任务执行时间
     */
    boolean taskTime() default true;

    /**
     * 是否把函数结果加入description
     * 加入格式为：  结果: xxxx
     */
    boolean result() default false;

    /**
     * Required
     * <p>
     * 日志描述
     * 支持SpEL条件语句
     */
    String description();
}
