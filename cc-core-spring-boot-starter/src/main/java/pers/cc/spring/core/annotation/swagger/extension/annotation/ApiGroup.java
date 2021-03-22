package pers.cc.spring.core.annotation.swagger.extension.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 */
@Documented
@Target({METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface ApiGroup {
    Class<?> value();
}
