package com.souther.cloud.design.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.souther.cloud.design.strategy.ILoginStrategy;
import com.souther.cloud.entity.UmsUserOauths;
import com.souther.cloud.utils.JwtUtils;
import com.souther.cloud.utils.ValidationUtil;
import com.souther.cloud.vo.po.LoginStrategyPO;
import com.souther.could.exception.Result;
import com.souther.could.exception.ResultCode;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 * @Description: 账号密码登录
 * @Author souther
 * @Date: 2021/1/31 18:33
 */
@Component
public class AccountLoginStrategy extends ILoginStrategy {

  @Override
  public Integer type() {
    return 1;
  }

  @Override
  public Result<String> login(LoginStrategyPO po) {

    /** po check **/
    ValidationUtil.validateBean(po, LoginStrategyPO.General.class).hasErrors();

    /** db **/
    UmsUserOauths db = super.umsUserOauthsMapper
        .selectOne(new LambdaQueryWrapper<UmsUserOauths>()
            .select(UmsUserOauths::getCredential, UmsUserOauths::getUserId)
            .eq(UmsUserOauths::getIdentityType, 1)
            .eq(UmsUserOauths::getIdentifier, po.getIdentifier())
        );

    /** db check **/
    if (Objects.isNull(db)) {
      return Result.error(ResultCode.FRONT_NOT_EXIST);
    }
    if (!po.getCredential().equals(db.getCredential())) {
      return Result.error(ResultCode.FRONT_CREDENTIAL);
    }

    String jwt = JwtUtils.getJwt(db.getUserId());

    return Result.success(jwt);
  }
}
