package pers.cc.spring.data.elasticsearch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author chengce
 * @version 2018-06-22 23:25
 */
//@Configuration
//@Slf4j
//    @Document(indexName = )
public class ElasticSearchConfig {

//    @Value("${spring.totalYData.elasticsearch.cluster-nodes:''}")
//    private String clusterNodes;
//    @Value("${spring.totalYData.elasticsearch.cluster-name:''}")
//    private String clusterName;
//    private RestHighLevelClient restHighLevelClient;
//
//
//    @Override
//    public Class<RestHighLevelClient> getObjectType() {
//        return RestHighLevelClient.class;
//    }
//
//    @Override
//    public RestHighLevelClient createInstance() {
//        return buildClient();
//    }
//
//    @Override
//    public void destroy() {
//        try {
//            if (restHighLevelClient != null) {
//                restHighLevelClient.close();
//            }
//        } catch (final Exception e) {
//            logger.error("Error closing ElasticSearch client: ", e);
//        }
//    }
//
////    @Override
////    public boolean isSingleton() {
////        return false;
////    }
//
////    @Bean
//    public RestHighLevelClient buildClient() {
//        try {
//            restHighLevelClient = new RestHighLevelClient(
//                    RestClient.builder(
//                            new HttpHost(clusterNodes, 9200, "http"),
//                            new HttpHost(clusterNodes, 9300, "http")));
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return restHighLevelClient;
//    }
}
