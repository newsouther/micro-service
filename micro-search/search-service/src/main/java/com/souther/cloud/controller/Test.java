package com.souther.cloud.controller;

import com.souther.cloud.utils.ElasticsearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.io.IOException;
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

    /**
     * 简单查询
     * @throws IOException
     */
    @org.junit.Test
    public void simpleFind() throws IOException {
        Map<String, Object> stringObjectMap = elasticsearchUtil.searchDataById("gateway", "msRCjnkBJu_rq-EGbiKr", "message");
        log.info(stringObjectMap.toString());
    }
}
