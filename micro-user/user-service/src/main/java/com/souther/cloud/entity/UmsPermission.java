package com.souther.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UmsPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 路径
     */
    private String uri;

    /**
     * 排序 默认0 数值大优先级高
     */
    private Integer sort;

    /**
     * 启用状态；0->禁用；1->启用
     */
    private Boolean status;

    /**
     * 描述
     */
    private String remark;

    /************************ 关联  ************************/

    // 拥有资源权限角色ID集合
    @TableField(exist = false)
    private List<Long> roleIds;

}
