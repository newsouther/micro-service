package com.souther.cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/14 15:17
 */
@RestController
public class TestController {

  @GetMapping("test")
  public String test() {
    return "oms msg";
  }
}
