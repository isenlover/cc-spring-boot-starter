package pers.cc.elasticsearch.configuration;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import pers.cc.elasticsearch.annotation.EnableElasticsearchRepositories;

import java.lang.annotation.Annotation;

/**
 * @author chengce
 * @version 2018-07-11 23:25
 */
public class ElasticsearchRepositoriesAutoConfigureRegistrar extends AbstractRepositoryConfigurationSourceSupport {
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableElasticsearchRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return ElasticsearchRepositoriesAutoConfigureRegistrar.EnableElasticsearchRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new ElasticsearchRepositoryConfigurationExtensionSupport();
    }

    @EnableElasticsearchRepositories
    private static class EnableElasticsearchRepositoriesConfiguration {
        private EnableElasticsearchRepositoriesConfiguration() {
        }
    }
}
