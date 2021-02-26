package com.souther.cloud.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.souther.cloud.design.factory.LoginHandel;
import com.souther.cloud.vo.po.LoginStrategyPO;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/2/1 9:56
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
public class TestCode {

  @Resource
  private LoginHandel loginHandel;

  //登录
  @Test
  public void login() {
    LoginStrategyPO po = new LoginStrategyPO();
    ValidationUtil.validateBean(po).hasErrors();
    loginHandel.login(1,po);
  }

  //生成雪花id
  @Test
  public void uid() {
    Long ID = IdWorker.getId();
    System.out.println(ID);
  }

}
