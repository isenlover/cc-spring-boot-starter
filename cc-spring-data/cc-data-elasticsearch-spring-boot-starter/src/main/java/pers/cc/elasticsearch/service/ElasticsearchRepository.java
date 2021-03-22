package pers.cc.elasticsearch.service;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * @author chengce
 * @version 2018-07-11 14:26
 */
@NoRepositoryBean
public interface ElasticsearchRepository<T, ID> extends Repository<T, ID> {

}
