package pers.cc.elasticsearch.configuration;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import pers.cc.elasticsearch.annotation.EnableElasticsearchRepositories;

import java.lang.annotation.Annotation;

/**
 * @author chengce
 * @version 2018-07-11 23:30
 */
public class ElasticsearchRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableElasticsearchRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new ElasticsearchRepositoryConfigurationExtensionSupport();
    }
}
