package com.souther.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Auther: souther
 * @Date: 2020/8/13 14:44
 * @Description:
 */
@FeignClient(name = "micro-oms")
public interface OmsFeign {

    @GetMapping("test")
    String test();
}
