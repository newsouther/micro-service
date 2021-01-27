package com.souther.cloud.controller;

import com.souther.cloud.service.AuthService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/9 14:18
 */
@AllArgsConstructor
@RestController
public class AuthController {

  private AuthService authService;

  @GetMapping("/publicKey")
  public Map<String, Object> getKey() {
    return authService.getKey();
  }

  @GetMapping("/getJwt")
  public String getJwt() {
    return authService.getJwt();
  }

}
