package pers.cc.spring.data.elasticsearch.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2018-06-15 00:57
 */
public class ElasticsearchConfigurationImport implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{ElasticsearchConfiguration.class.getName()};
    }
}
