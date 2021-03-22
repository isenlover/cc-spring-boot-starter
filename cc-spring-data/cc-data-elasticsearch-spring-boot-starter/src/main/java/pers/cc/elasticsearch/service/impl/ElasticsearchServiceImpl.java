package pers.cc.elasticsearch.service.impl;

import org.apache.http.Header;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pers.cc.elasticsearch.annotation.*;
import pers.cc.elasticsearch.exception.ElasticsearchException;
import pers.cc.elasticsearch.exception.ElasticsearchExceptionConstant;
import pers.cc.elasticsearch.filed.MappingParameter;
import pers.cc.elasticsearch.service.ElasticsearchService;
import pers.cc.elasticsearch.util.ElasticsearchUtils;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.core.util.other.MathUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengce
 * @version 2018-07-09 00:35
 */
@Component
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    public String createIndex(Class indexClass) {
        Document document = ElasticsearchUtils.getDocument(indexClass);
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(document.indexName());
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", document.shards())
                .put("index.number_of_replicas", document.replicas())
                .put("index.refresh_interval", document.refreshInterval())
                .put("index.store.type", document.indexStoreType().name()));
        Map<String, Object> rootMap = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        ClassUtils.getClassAllFields(indexClass).forEach(field -> {
            Map<String, Object> fieldMap = new HashMap<>();
            Field annotationField = field.getAnnotation(Field.class);
            fieldMap.put(MappingParameter.index.name(), true);
            fieldMap.put(MappingParameter.store.name(), false);
            fieldMap.put(MappingParameter.boost.name(), 1.0f);
            if (annotationField != null) {
                if (annotationField.type().equals(FieldType.Auto)) {
                    fieldMap.put(MappingParameter.type.name(), ElasticsearchUtils.getAutoFieldType(field));
                    if (ElasticsearchUtils.getAutoFieldType(field).equals("text")) {
                        fieldMap.put(MappingParameter.fielddata.name(), annotationField.fielddata());
                    }
                } else {
                    fieldMap.put(MappingParameter.type.name(), annotationField.type().name().toLowerCase());
                    if (annotationField.type().equals(FieldType.Text)) {
                        fieldMap.put(MappingParameter.fielddata.name(), annotationField.fielddata());
                    }
                }
                fieldMap.put(MappingParameter.index.name(), annotationField.index());
                fieldMap.put(MappingParameter.store.name(), annotationField.store());
                fieldMap.put(MappingParameter.boost.name(), annotationField.boost());
                if (!annotationField.type().equals(FieldType.Auto)) {
                    if (annotationField.type().equals(FieldType.Date) || field.getType().equals(
                            Date.class)) {
                        if (annotationField.format().length > 0) {
                            if (annotationField.format().length == 1 && annotationField.format()[0].equals(
                                    DateFormat.custom)) {
                                fieldMap.put(MappingParameter.format.name(), annotationField.dateFormatPattern());
                            } else {
                                fieldMap.put(MappingParameter.format.name(),
                                        ElasticsearchUtils.getFormats(annotationField.format()));
                            }
                        }
                    }
                }
                if (!annotationField.analyzer().equals("")) {
                    fieldMap.put(MappingParameter.analyzer.name(), annotationField.analyzer());
                }
                if (!annotationField.searchAnalyzer().equals("")) {
                    fieldMap.put(MappingParameter.search_analyzer.name(), annotationField.searchAnalyzer());
                }
            } else {
                fieldMap.put(MappingParameter.type.name(), ElasticsearchUtils.getAutoFieldType(field));
            }
            boolean createKeywordFields = annotationField != null ? (annotationField.fieldsKeyword() && annotationField.type().equals(
                    FieldType.Text)) : ElasticsearchUtils.getAutoFieldType(
                    field).equals("text");
            if (!createKeywordFields) {
                Map<String, Object> keywordFieldsMap = new HashMap<>();
                Map<String, Object> keywordFieldsTypeMap = new HashMap<>();
                keywordFieldsTypeMap.put(MappingParameter.type.name(), "keyword");
                keywordFieldsMap.put("keyword", keywordFieldsTypeMap);
                fieldMap.put(MappingParameter.fields.name(), keywordFieldsMap);
            }
            properties.put(field.getName(), fieldMap);
        });

        Map<String, Object> typeMap = new HashMap<>();
        typeMap.put("properties", properties);
        rootMap.put(document.type(), typeMap);
        createIndexRequest.mapping(document.type(), rootMap);
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest);
            return createIndexResponse.index();
        } catch (IOException e) {
            throw new ElasticsearchException("创建索引失败");
        }
    }

    @Override
    public void deleteIndex(String index) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        try {
            restHighLevelClient.indices().delete(deleteIndexRequest);
        } catch (org.elasticsearch.ElasticsearchException e) {
            if (e.status().equals(RestStatus.NOT_FOUND)) {
                throw new ElasticsearchException("删除索引失败,索引不存在");
            }
            throw new ElasticsearchException("删除索引失败", e.status().name(), -1);
        } catch (IOException e) {
            throw new ElasticsearchException("删除索引失败");
        }
    }

    @Override
    public void deleteIndex(Class indexClass) {
        String indexName = ElasticsearchUtils.getIndexName(indexClass);
        if (indexName != null) {
            deleteIndex(indexName);
        } else {
            throw new ElasticsearchException("删除索引失败");
        }
    }

    @Override
    public boolean indexExist(String index) {
        GetIndexRequest getIndexRequest = new GetIndexRequest()
                .indices(index)
                .local(false)
                .humanReadable(true)
                .includeDefaults(true);

        try {
            return restHighLevelClient.indices().exists(getIndexRequest);
        } catch (IOException e) {
            throw new ElasticsearchException("判断索引失败");
        }
    }

    @Override
    public boolean indexExist(Class indexClass) {
        String indexName = ElasticsearchUtils.getIndexName(indexClass);
        if (indexName != null) {
            return indexExist(indexName);
        } else {
            throw new ElasticsearchException("删除索引失败");
        }
    }


    public void createAsync(Object object, ActionListener<IndexResponse> listener, Header... headers) {
        createAsync(object, listener, true, headers);
    }

    @Override
    public void createAsync(Object object, ActionListener<IndexResponse> listener, boolean idAsKey, Header... headers) {
        Document document = ElasticsearchUtils.getDocument(object.getClass());
        if (document.createIndex() && !indexExist(object.getClass())) {
            createIndex(object.getClass());
        }
        Map<String, Object> paramValueMap = ClassUtils.objectToMap(object);
        String id = MathUtils.getSimpleUUID();
        if (idAsKey && paramValueMap.containsKey("id")) {
            paramValueMap.put("id", id);
        }
        IndexRequest indexRequest = new IndexRequest(document.indexName(), document.type(), id)
                .source(paramValueMap)
                // 立即更新
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        restHighLevelClient.indexAsync(indexRequest, listener, headers);
    }

    @Override
    public void updateAsync(Object object, ActionListener<UpdateResponse> listener, Header... headers) {
        Document document = ElasticsearchUtils.getDocument(object.getClass());
        Map<String, Object> paramValueMap = ClassUtils.objectToMap(object);
        String id = (String) paramValueMap.get("id");
        if (CommonUtils.isNotEmpty(id)) {
            UpdateRequest updateRequest = new UpdateRequest(document.indexName(), document.type(), id)
                    .doc(paramValueMap)
                    .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            restHighLevelClient.updateAsync(updateRequest, listener, headers);
        } else {
            throw new ElasticsearchException(ElasticsearchExceptionConstant.EXCEPTION_UPDATE);
        }
    }

    @Override
    public void saveAsync(Object object, ActionListener<UpdateResponse> listener, Header... headers) {
        Document document = ElasticsearchUtils.getDocument(object.getClass());
        Map<String, Object> paramValueMap = ClassUtils.objectToMap(object);
        String id = (String) paramValueMap.get("id");
        UpdateRequest updateRequest = new UpdateRequest(document.indexName(), document.type(), id)
                .doc(paramValueMap)
                .upsert("te22", "ttt")
                .setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        restHighLevelClient.updateAsync(updateRequest, listener, headers);
    }
}
