package com.souther.cloud.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户授权信息表
 * </p>
 *
 * @author souther
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UmsUserOauths implements Serializable {

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
     * 登录类型
     */
    private Boolean identityType;

    /**
     * 手机号/邮箱/第三方的唯一标识
     */
    private String identifier;

    /**
     * 配合identifier需要的额外信息，用于确定用户标识，如微信的union_id
     */
    private String identifierExtra;

    /**
     * 密码凭证/access_token (自建账号的保存密码, 第三方的保存 token)
     */
    private String credential;


}
