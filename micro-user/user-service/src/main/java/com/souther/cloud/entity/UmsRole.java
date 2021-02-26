package com.souther.cloud.entity;

import java.io.Serializable;
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
public class UmsRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限值
     */
    private String perms;

    /**
     * 排序 （默认0 数值大优先级高）
     */
    private Integer sort;

    /**
     * 描述
     */
    private String remark;

    /**
     * 角色状态（0正常 1停用）
     */
    private Boolean status;


}
