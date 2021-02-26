package com.souther.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.souther.cloud.entity.UmsUserLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户登陆日志表 Mapper 接口
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@Mapper
public interface UmsUserLoginLogMapper extends BaseMapper<UmsUserLoginLog> {

}
