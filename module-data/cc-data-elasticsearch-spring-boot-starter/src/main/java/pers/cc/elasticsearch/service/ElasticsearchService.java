package pers.cc.elasticsearch.service;

import org.apache.http.Header;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

/**
 * @author chengce
 * @version 2018-07-09 00:35
 */
public interface ElasticsearchService {

    String createIndex(Class indexClass);

    void deleteIndex(String index);

    void deleteIndex(Class indexClass);

    boolean indexExist(String index);

    boolean indexExist(Class indexClass);

    /**
     * 新增文档
     * 会自动选名称为id的字段作为_id值
     *
     * @param object 类
     * @param listener 回调
     * @param headers  headers
     */
    void createAsync(Object object, ActionListener<IndexResponse> listener, Header... headers);

    /**
     * 新增文档
     *
     * @param object   类
     * @param idAsKey  是否把id字段作为_id
     * @param listener 回调
     * @param headers  headers
     */
    void createAsync(Object object, ActionListener<IndexResponse> listener, boolean idAsKey, Header... headers);

    /**
     * 更新文档
     * 不会更新空字段
     *
     * @param object   类
     * @param listener 回调
     * @param headers  headers
     */
    void updateAsync(Object object, ActionListener<UpdateResponse> listener, Header... headers);

    /**
     * 新增或更新，若文档不存在则新增
     *
     * @param object   类
     * @param listener 回调
     * @param headers  headers
     */
    @Deprecated
    void saveAsync(Object object, ActionListener<UpdateResponse> listener, Header... headers);
}
