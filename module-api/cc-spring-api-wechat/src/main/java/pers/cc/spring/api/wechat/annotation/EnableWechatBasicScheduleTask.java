package pers.cc.spring.api.wechat.annotation;

import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-01 17:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableScheduling
public @interface EnableWechatBasicScheduleTask {
}
