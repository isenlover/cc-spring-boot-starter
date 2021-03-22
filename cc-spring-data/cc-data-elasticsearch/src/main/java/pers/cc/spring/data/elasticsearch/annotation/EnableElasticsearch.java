package pers.cc.spring.data.elasticsearch.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.data.elasticsearch.config.ElasticsearchConfigurationImport;

import java.lang.annotation.*;

/**
 * 自动注入
 *
 * @author chengce
 * @version 2018-04-30 20:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ElasticsearchConfigurationImport.class)
public @interface EnableElasticsearch {
}
