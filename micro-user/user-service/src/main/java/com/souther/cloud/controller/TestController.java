package com.souther.cloud.controller;

import com.souther.cloud.client.OmsFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/14 15:17
 */
@RestController
public class TestController {

    @Resource
    private OmsFeign omsFeign;

    @GetMapping("test")
    public String test() {
        System.out.println(omsFeign.test());
        return "user msg";
    }
}
