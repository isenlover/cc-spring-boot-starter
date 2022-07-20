package pers.cc.spring.data.elasticsearch.service;

/**
 * Elasticsearch 基础库
 *
 * @author chengce
 * @version 2018-04-25 14:21
 */
public interface BaseElasticsearchService {

    /**
     * 索引是否存在
     *
     * @param clazz 类
     * @return 是否存在
     */
    <T> boolean indexExist(Class<T> clazz);

    /**
     * 判断索引是否存在，如果存在返回false
     * 如果不存在则简历
     *
     * @param clazz 类
     * @return 创建是否成功
     */
    <T> boolean createIndex(Class<T> clazz);

    /**
     * 删除索引
     *
     * @param clazz 类
     * @param <T>   实例
     * @return 删除结果
     */
    <T> boolean removeIndex(Class<T> clazz);
}
