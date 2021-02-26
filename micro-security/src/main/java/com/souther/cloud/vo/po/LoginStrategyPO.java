package com.souther.cloud.vo.po;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/31 18:25
 */
@Setter
@Getter
public class LoginStrategyPO {

  /**
   * 登录类型
   */
  @NotNull
  private Boolean identityType;

  /**
   * 手机号/邮箱/第三方的唯一标识
   */
  @NotBlank(groups = General.class)
  private String identifier;

  /**
   * 密码凭证/access_token (自建账号的保存密码, 第三方的保存 token)
   */
  @NotBlank(groups = General.class)
  private String credential;

  /**
   * 唯一标识和凭证不能为空
   */
  public interface General{};

}
