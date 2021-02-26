package com.souther.cloud.design.strategy;

import com.souther.cloud.mapper.UmsUserOauthsMapper;
import com.souther.cloud.vo.po.LoginStrategyPO;
import com.souther.could.exception.Result;
import javax.annotation.Resource;

public abstract class ILoginStrategy {

  @Resource
  protected UmsUserOauthsMapper umsUserOauthsMapper;

  public abstract Integer type();

  public abstract Result<String> login(LoginStrategyPO loginStrategyPO);

}
