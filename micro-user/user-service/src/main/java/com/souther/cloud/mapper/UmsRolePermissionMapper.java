package com.souther.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.souther.cloud.entity.UmsRolePermission;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@Mapper
public interface UmsRolePermissionMapper extends BaseMapper<UmsRolePermission> {

  @Select("select role_id from ums_role_permission where permission_id=#{permissionId}")
  List<Long> listByPermissionId(Long permissionId);

}
