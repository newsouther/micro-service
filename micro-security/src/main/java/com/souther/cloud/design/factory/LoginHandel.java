package com.souther.cloud.design.factory;

import com.souther.cloud.design.strategy.ILoginStrategy;
import com.souther.cloud.vo.po.LoginStrategyPO;
import com.souther.could.exception.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/31 18:18
 */
@Component
public class LoginHandel {

  private Map<Integer, ILoginStrategy> loginStrategyMap = new HashMap<>();

  public LoginHandel(List<ILoginStrategy> list) {
    list.forEach(item -> loginStrategyMap.put(item.type(), item));
  }

  public Result<String> login(Integer type, LoginStrategyPO po) {
    return loginStrategyMap.get(type).login(po);
  }

}
