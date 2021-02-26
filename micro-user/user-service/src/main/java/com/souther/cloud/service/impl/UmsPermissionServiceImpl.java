package com.souther.cloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.souther.cloud.constant.Constant;
import com.souther.cloud.constant.RedisKeyEnum;
import com.souther.cloud.entity.UmsPermission;
import com.souther.cloud.mapper.UmsPermissionMapper;
import com.souther.cloud.service.IUmsPermissionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@Service
public class UmsPermissionServiceImpl extends
    ServiceImpl<UmsPermissionMapper, UmsPermission> implements IUmsPermissionService {

  @Resource
  private RedisTemplate redisTemplate;

  @Override
  public boolean updateRedisOfRolePermission() {

    redisTemplate.delete(RedisKeyEnum.RESOURCE_ROLES_KEY.getKey());

    List<UmsPermission> dbUmsPermissions = this.baseMapper.listForPermissionRoles();
    Map<String, List<String>> permissionRolesMap = new TreeMap<>();
    Optional.ofNullable(dbUmsPermissions).orElse(new ArrayList<>()).forEach(permission -> {

      // 转换 roleId -> ROLE_{roleId}
      List<String> roles = Optional.ofNullable(permission.getRoleIds()).orElse(new ArrayList<>())
          .stream().map(roleId -> Constant.AUTHORITY_PREFIX + roleId)
          .collect(Collectors.toList());

      if (CollectionUtil.isNotEmpty(roles)) {
        permissionRolesMap.put(permission.getUri(), roles);
      }

      redisTemplate.opsForHash().putAll(RedisKeyEnum.RESOURCE_ROLES_KEY.getKey(), permissionRolesMap);
    });

    return true;
  }
}
