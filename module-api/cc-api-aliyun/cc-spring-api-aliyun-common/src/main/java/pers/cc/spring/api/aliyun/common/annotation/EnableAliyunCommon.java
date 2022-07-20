package pers.cc.spring.api.aliyun.common.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import pers.cc.spring.api.aliyun.common.config.AliyunImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-14 22:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan("pers.cc.spring.api.aliyun.common")
@Import(AliyunImport.class)
public @interface EnableAliyunCommon {

}
