package com.souther.cloud.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RedisKeyEnum {

  ;

  private String key;

  public String getKey() {
    return Constant.REDIS_PREFIX + key;
  }
}
