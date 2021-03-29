package com.souther.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: souther
 * @Date: 2020/8/16 23:57
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MicroOmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroOmsApplication.class, args);
  }

}
