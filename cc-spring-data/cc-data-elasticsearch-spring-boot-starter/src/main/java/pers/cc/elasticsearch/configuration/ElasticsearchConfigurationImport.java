package pers.cc.elasticsearch.configuration;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chengce
 * @version 2018-07-05 18:45
 */
public class ElasticsearchConfigurationImport implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[0];
    }
}
