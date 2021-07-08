package com.souther.cloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.souther.cloud.dto.GoodsParamPO;
import com.souther.cloud.dto.TestMsgDto;
import com.souther.cloud.utils.ElasticsearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/6/23 15:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("dev")
@Slf4j
public class Test {

    @Resource
    private ElasticsearchUtil elasticsearchUtil;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 简单查询
     *
     * @throws IOException
     */
    @org.junit.Test
    public void simpleFind() throws IOException {
        Map<String, Object> stringObjectMap = elasticsearchUtil.searchDataById("testindex", "1409789963064840193", null);
        log.info(stringObjectMap.toString());
    }

    /**
     * 索引
     *
     * @throws IOException
     */
    @org.junit.Test
    public void indexAll() throws IOException {
        //1.全部索引
        String[] allIndex = elasticsearchUtil.allIndex();
        log.info(Arrays.toString(allIndex));

        //2.创建索引
//        invalid_index_name_exception: must be lowercase ....
//        boolean result = elasticsearchUtil.createIndex("testindex");
//        log.info(result + "");
    }

    /**
     * 添加数据
     */
    @org.junit.Test
    public void addData() throws IOException {
        TestMsgDto msgDto = TestMsgDto.builder()
                .title("标题")
                .type(1)
                .dataDeleted(false)
                .createTime(LocalDateTime.now())
                .ids(Arrays.asList(1, 2, 3))
                .build();
        String result = elasticsearchUtil.addData(msgDto, "testindex");
        log.info(result);
    }

    /**
     * 条件搜索 -- 参考谷粒商城
     *
     * @throws IOException
     */
    @org.junit.Test
    public void searchQuery() throws JsonProcessingException {
//        elasticsearchUtil.searchListData();

        //构建参数
        GoodsParamPO paramPO = new GoodsParamPO();

        SearchResult result = null;
        // 1.准备检索请求
        SearchRequest searchRequest = buildSearchRequest(paramPO);
        SearchResponse response = null;
        try {
            // 2.执行检索请求
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 3.分析响应数据
//            result = buildSearchResult(response, Param);
        } catch (IOException e) {
            log.error("es搜索异常：", e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(objectMapper.writeValueAsString(response));
    }

    private SearchRequest buildSearchRequest(GoodsParamPO paramPO) {
        // 帮我们构建DSL语句的
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //构建请求
        SearchRequest request = new SearchRequest("testindex");

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("title", "标")); //包含vaule
        boolQuery.must(QueryBuilders.termQuery("title", "标"));

        sourceBuilder.query(boolQuery);

        return request.source(sourceBuilder);
    }
}
