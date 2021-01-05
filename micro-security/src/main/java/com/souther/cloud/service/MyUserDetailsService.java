package com.souther.cloud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author souther
 * @Date: 2020/12/24 16:31
 */
public interface MyUserDetailsService extends UserDetailsService {

  //通过唯一标识获取用户登录信息
  UserDetails loadUserByIdentifier(String identifier);

}
