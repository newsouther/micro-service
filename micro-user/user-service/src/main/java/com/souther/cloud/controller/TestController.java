package com.souther.cloud.controller;

import com.souther.cloud.client.OmsFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/14 15:17
 */
@Slf4j
@RestController
public class TestController {

    @Resource
    private OmsFeign omsFeign;

    @GetMapping("test")
    public String test() {
        log.info("user-service：" + omsFeign.test());
        return "user msg";
    }
}
