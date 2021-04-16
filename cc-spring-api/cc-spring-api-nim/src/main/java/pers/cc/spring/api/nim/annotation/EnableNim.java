package pers.cc.spring.api.nim.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.api.nim.config.NimConfigurationImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-01 17:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(NimConfigurationImport.class)
public @interface EnableNim {
}
