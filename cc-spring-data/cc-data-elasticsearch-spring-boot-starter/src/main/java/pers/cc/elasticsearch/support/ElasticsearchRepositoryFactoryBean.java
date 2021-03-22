package pers.cc.elasticsearch.support;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.TransactionalRepositoryFactoryBeanSupport;

/**
 * @author chengce
 * @version 2018-07-11 22:05
 */
public class ElasticsearchRepositoryFactoryBean<T extends Repository<S, ID>, S, ID>
        extends TransactionalRepositoryFactoryBeanSupport<T, S, ID> {

    protected ElasticsearchRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        return new ElasticsearchRepositoryFactory();
    }
}
