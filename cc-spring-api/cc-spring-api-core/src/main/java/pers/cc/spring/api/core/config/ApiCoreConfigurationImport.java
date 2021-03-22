package pers.cc.spring.api.core.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2018-06-15 01:19
 */
public class ApiCoreConfigurationImport implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{ApiCoreConfiguration.class.getName()};
    }
}
