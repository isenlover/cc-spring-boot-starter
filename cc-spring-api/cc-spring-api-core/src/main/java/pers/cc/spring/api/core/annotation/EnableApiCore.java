package pers.cc.spring.api.core.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.api.core.config.ApiCoreConfigurationImport;

import java.lang.annotation.*;

/**
 * 自动注入配置
 * 会自动加载 jwt
 *
 * @author chengce
 * @version 2018-04-30 20:36
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ApiCoreConfigurationImport.class)
public @interface EnableApiCore {
}
