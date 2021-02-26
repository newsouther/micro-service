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
public class UmsMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 父级ID 默认0
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单级数 默认0
     */
    private Integer level;

    /**
     * 菜单排序 默认0 数值大优先级高
     */
    private Integer sort;

    /**
     * 前端名称
     */
    private String name;

    /**
     * 前端图标
     */
    private String icon;

    /**
     * 前端隐藏 默认0
     */
    private Boolean hidden;


}
