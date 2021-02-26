package com.souther.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.souther.cloud.entity.UmsPermission;
import java.util.List;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
public interface UmsPermissionMapper extends BaseMapper<UmsPermission> {

  @Select("select uri from ums_permission")
  @Results({
      @Result(id = true, column = "id", property = "id"),
      @Result(property = "roleIds", column = "id", many = @Many(select = "com.souther.cloud.UmsRolePermissionMapper.listByPermissionId"))
  })
  List<UmsPermission> listForPermissionRoles();

}
