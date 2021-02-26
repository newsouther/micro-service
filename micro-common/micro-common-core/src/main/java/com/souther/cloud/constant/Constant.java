package com.souther.cloud.constant;


public final class Constant {

  /**
   * 用户Token
   */
  public static final String USER_TOKEN_HEADER = "Authorization";

  /*** ==================== REDIS相关========================= **/

  /**
   * 项目redis缓存前缀
   */
  public static final String REDIS_PREFIX = "micro:";

  /**
   * JWT存储权限前缀
   */
  public static final String AUTHORITY_PREFIX = "ROLE_";

  /**
   * JWT存储权限属性
   */
  public static final String AUTHORITY_CLAIM_NAME = "authorities";

}
