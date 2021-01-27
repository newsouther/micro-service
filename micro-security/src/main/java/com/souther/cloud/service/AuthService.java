package com.souther.cloud.service;

import java.util.Map;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/9 14:34
 */
public interface AuthService {

  Map<String, Object> getKey();

  String getJwt();
}
