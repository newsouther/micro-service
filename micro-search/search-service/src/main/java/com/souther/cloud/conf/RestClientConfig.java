package com.souther.cloud.conf;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: es配置：统一使用rest，弃用api
 * @Author souther
 * @Date: 2021/6/22 17:42
 */
@Configuration
public class RestClientConfig {

    public RestHighLevelClient esClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("elasticsearch", 9200, "http")));
        return client;
    }

}
