package com.souther.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Auther: souther
 * @Date: 2020/8/16 23:57
 * @Description:
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MicroUserApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroUserApplication.class, args);
  }

}
