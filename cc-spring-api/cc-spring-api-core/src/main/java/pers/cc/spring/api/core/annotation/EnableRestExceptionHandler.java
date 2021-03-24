package pers.cc.spring.api.core.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.api.core.config.ApiCoreConfigurationImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2021-03-24 22:52
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ApiCoreConfigurationImport.class)
public @interface EnableRestExceptionHandler {
}
