package com.souther.cloud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户登陆日志表
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UmsUserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id外键
     */
    private Long userId;

    /**
     * ip
     */
    private Integer ip;

    /**
     * 城市
     */
    private String city;

    /**
     * 状态：1.成功 2.失败
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
