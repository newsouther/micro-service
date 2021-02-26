package com.souther.cloud.service;

import com.souther.cloud.entity.UmsPermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
public interface IUmsPermissionService extends IService<UmsPermission> {

  //手动更新 权限-》角色 redis缓存
  boolean updateRedisOfRolePermission();

}
