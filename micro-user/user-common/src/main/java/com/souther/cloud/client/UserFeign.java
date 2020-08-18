package com.souther.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: souther
 * @Date: 2020/8/13 14:44
 * @Description:
 */
@FeignClient(name = "micro-user")
public interface UserFeign {

}
