package com.souther.cloud.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RedisKeyEnum {

  /*********************** 安全 *****************/
  RESOURCE_ROLES_KEY("security:resourceRoles")
  ;

  private String key;

  public String getKey() {
    return Constant.REDIS_PREFIX + key;
  }
}
