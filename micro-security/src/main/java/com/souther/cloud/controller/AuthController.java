package com.souther.cloud.controller;

import com.souther.cloud.design.factory.LoginHandel;
import com.souther.cloud.service.AuthService;
import com.souther.cloud.vo.po.LoginStrategyPO;
import com.souther.could.exception.Result;
import java.util.Map;
import javax.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/9 14:18
 */
@AllArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthController {

  private AuthService authService;

  @Resource
  private LoginHandel loginHandel;

  @PostMapping("login")
  public Result<String> login(@RequestParam Integer type, @RequestBody LoginStrategyPO po) {
    return loginHandel.login(type, po);
  }

  @GetMapping("/publicKey")
  public Map<String, Object> getKey() {
    return authService.getKey();
  }

  @GetMapping("/getJwt")
  public String getJwt() {
    return authService.getJwt();
  }

}
