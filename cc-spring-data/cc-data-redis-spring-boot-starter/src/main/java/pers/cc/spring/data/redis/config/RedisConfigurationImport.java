package pers.cc.spring.data.redis.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2018-06-15 01:04
 */
public class RedisConfigurationImport implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{RedisConfiguration.class.getName()};
    }
}
