package com.souther.cloud.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SecurityRedisKeyEnum {

  /**
   * 缓存client的redis key，这里是hash结构存储
   */
  CACHE_CLIENT_KEY("oauth_client_details"),

  /**
   * redis中授权token对应的key
   */
  REDIS_TOKEN_AUTH("auth:"),

  /**
   * redis中应用对应的token集合的key
   */
  REDIS_CLIENT_ID_TO_ACCESS("client_id_to_access:"),

  /**
   * redis中用户名对应的token集合的key
   */
  REDIS_UNAME_TO_ACCESS("uname_to_access:"),

  ACCESS("access:"),
  AUTH_TO_ACCESS("auth_to_access:"),
  REFRESH_AUTH("refresh_auth:"),
  ACCESS_TO_REFRESH("access_to_refresh:"),
  REFRESH("refresh:"),
  REFRESH_TO_ACCESS("refresh_to_access:"),

  ;

  private String key;

  public String getKey() {
    return Constant.REDIS_PREFIX + "security:" + key;
  }
}
