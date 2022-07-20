package pers.cc.elasticsearch.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pers.cc.elasticsearch.property.ElasticsearchProperties;
import pers.cc.elasticsearch.property.ElasticsearchTimeout;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chengce
 * @version 2018-07-05 18:29
 */
@Slf4j
@Configuration
@ConditionalOnClass(RestHighLevelClient.class)
@ComponentScan("pers.cc.elasticsearch")
//@Import(ElasticsearchRepositoriesAutoConfigureRegistrar.class)
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchAutoConfiguration {

    @Resource
    private ElasticsearchProperties elasticsearchProperties;

    @Bean
    RestHighLevelClient restHighLevelClient() {
        String[] clusterNodes = elasticsearchProperties.getClusterNodes().split(",");
        List<HttpHost> httpHosts = new ArrayList<>();
        for (String clusterNode : clusterNodes) {
            String ip = clusterNode;
            if (clusterNode.contains(":")) {
                ip = clusterNode.split(":")[0];
            }
            httpHosts.add(new HttpHost(ip, 9200));
        }

        ElasticsearchTimeout timeout = elasticsearchProperties.getTimeout();

        RestClientBuilder builder = RestClient.builder(
                httpHosts.toArray(new HttpHost[]{}))
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(timeout.getConnectTimeout())
                                .setSocketTimeout(timeout.getSocketTimeout()).setConnectionRequestTimeout(
                                        timeout.getConnectRequestTimeout()))
                .setMaxRetryTimeoutMillis(timeout.getMaxRetryTimeout());
        if (elasticsearchProperties.getUsername() != null && !"".equals(elasticsearchProperties.getUsername())) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(elasticsearchProperties.getUsername(),
                            elasticsearchProperties.getPassword()));
            builder.setHttpClientConfigCallback(
                    httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(
                            credentialsProvider));
        }

        return new RestHighLevelClient(builder);
    }
}
