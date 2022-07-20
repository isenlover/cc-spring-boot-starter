package pers.cc.spring.data.elasticsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import pers.cc.spring.data.elasticsearch.service.BaseElasticsearchService;

/**
 * Elasticsearch 基础接口实现
 *
 * @author chengce
 * @version 2018-04-25 14:24
 * @Deprecated 2022/6/9 19:35
 */
@Service
@Deprecated
public class BaseElasticsearchImpl implements BaseElasticsearchService {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public <T> boolean indexExist(Class<T> clazz) {
        return false;
    }

    @Override
    public <T> boolean createIndex(Class<T> clazz) {
        return false;
    }

    @Override
    public <T> boolean removeIndex(Class<T> clazz) {
        return false;
    }

//    @Override
//    public <T> boolean indexExist(Class<T> clazz) {
//        return elasticsearchTemplate.indexExists(clazz);
//    }
//
//    @Override
//    public <T> boolean createIndex(Class<T> clazz) {
//        if (indexExist(clazz)) {
//            return false;
//        }
//        return elasticsearchTemplate.createIndex(clazz);
//    }
//
//    @Override
//    public <T> boolean removeIndex(Class<T> clazz) {
//        if (indexExist(clazz)) {
//            return elasticsearchTemplate.deleteIndex(clazz);
//        }
//        return false;
//    }
}
