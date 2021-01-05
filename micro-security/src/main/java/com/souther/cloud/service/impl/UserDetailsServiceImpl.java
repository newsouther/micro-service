package com.souther.cloud.service.impl;

import com.souther.cloud.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/24 16:55
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements MyUserDetailsService {

  @Override
  public UserDetails loadUserByIdentifier(String identifier) {
    // todo 调用用户服务
    return null;
  }

  //这个应该是密码模式用的
  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return null;
  }
}
