package pers.cc.elasticsearch.support;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.PersistentEntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import pers.cc.elasticsearch.service.impl.ElasticsearchRepositoryImpl;

/**
 * @author chengce
 * @version 2018-07-11 21:03
 */
public class ElasticsearchRepositoryFactory extends RepositoryFactorySupport {
    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        System.out.println("cc: getEntityInformation" + domainClass);
        return null;
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation repositoryInformation) {
        System.out.println("cc: getTargetRepository" + repositoryInformation);
        return new ElasticsearchRepositoryImpl<>();
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata repositoryMetadata) {
        System.out.println("cc: getRepositoryBaseClass"  + repositoryMetadata);
        return ElasticsearchRepositoryImpl.class;
    }
}
