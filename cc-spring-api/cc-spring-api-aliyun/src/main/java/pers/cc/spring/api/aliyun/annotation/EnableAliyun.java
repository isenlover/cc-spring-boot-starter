package pers.cc.spring.api.aliyun.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.api.aliyun.config.AliyunConfigurationImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-14 22:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AliyunConfigurationImport.class)
public @interface EnableAliyun {

}
