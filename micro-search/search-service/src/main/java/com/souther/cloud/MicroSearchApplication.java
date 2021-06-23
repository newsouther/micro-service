package com.souther.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Auther: souther
 * @Date: 2020/8/16 23:57
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MicroSearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroSearchApplication.class, args);
  }

}
