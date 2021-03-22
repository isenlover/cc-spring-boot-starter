package pers.cc.elasticsearch.service.impl;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pers.cc.elasticsearch.annotation.DateFormat;
import pers.cc.elasticsearch.annotation.Document;
import pers.cc.elasticsearch.annotation.FieldType;
import pers.cc.elasticsearch.exception.ElasticsearchException;
import pers.cc.elasticsearch.filed.MappingParameter;
import pers.cc.elasticsearch.service.ElasticsearchRepository;
import pers.cc.elasticsearch.util.ElasticsearchUtils;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.core.util.other.MathUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chengce
 * @version 2018-07-11 14:36
 */
//@Repository
public class ElasticsearchRepositoryImpl<T, ID> implements ElasticsearchRepository<T, ID> {
    private T indexObject;

    private ID id;

    private Class indexClass;

    private List<Field> indexFields;

    @Autowired
    RestHighLevelClient client;

//    public SimpleElasticsearchRepository() {
//        this.indexClass = indexObject.getClass();
//        this.indexFields = ClassUtils.getClassAllFields(indexClass);
//    }


}
