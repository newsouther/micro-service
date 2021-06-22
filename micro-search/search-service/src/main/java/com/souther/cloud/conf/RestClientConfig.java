package com.souther.cloud.conf;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: es配置：统一使用rest，弃用api
 * @Author souther
 * @Date: 2021/6/22 17:42
 */
@Configuration
public class RestClientConfig {

    @Value("${elasticsearch.clientIp}")
    private String clientIp;

    @Value("${elasticsearch.httpPort}")
    private int httpPort;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    public RestHighLevelClient esClient() {

        //认证
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(clientIp, httpPort, "http"))
                        .setHttpClientConfigCallback(
                                (HttpAsyncClientBuilder httpAsyncClientBuilder) ->
                                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                        )
        );
        return client;
    }

}
