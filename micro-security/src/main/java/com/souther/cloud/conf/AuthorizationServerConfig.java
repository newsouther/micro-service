package com.souther.cloud.conf;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Description: 认证服务器配置
 * @Author souther
 * @Date: 2020/12/24 10:44
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private AuthenticationManager authenticationManager;

  private UserDetailsService userDetailsService;

//  private WebResponseExceptionTranslator webResponseExceptionTranslator;

  private TokenGranter tokenGranter;

  private TokenStore tokenStore;

  /**
   * 可以配置的东西：身份认证器，认证方式，token储存位置TokenStore， TokenGranter，OAuth2RequestFactory，认证管理器authenticationManager
   * accessToken和refreshToken的默认超时时间,是否支持刷新Token 配置grant_type模式 各种配置可以参考：https://www.programcreek.com/java-api-examples/?class=org.springframework.security.oauth2.provider.token.TokenEnhancerChain&method=setTokenEnhancers
   *
   * @param endpoints
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {

    endpoints
        //令牌的储存
        .tokenStore(tokenStore)
        //认证管理器 账号密码模式需要
        .authenticationManager(authenticationManager)
        //用户账号密码认证
        .userDetailsService(userDetailsService)
        //授权码模式：授权码管理(code的存储)
        //.authorizationCodeServices(authorizationCodeServices)
        // todo 认证错误的处理
        //.exceptionTranslator(webResponseExceptionTranslator)
        // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
        //      1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
        //      2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
        //.reuseRefreshTokens(true)
        .tokenGranter(
            tokenGranter);  //配置授权模式。支持自定义：创建tokenGranter，加载自定义授权模式（继承AbstractTokenGranter）

  }

  /**
   * 配置令牌端点(Token Endpoint)的安全约束
   *
   * @param security
   */
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security
        .tokenKeyAccess("isAuthenticated()")
        .checkTokenAccess("permitAll()")
        //让/oauth/token支持client_id以及client_secret作登录认证
        //一般是针对 password 模式和 client_credentials
        //另一个说法是：支持表单认证。(我的理解是需要浏览器web的)
        .allowFormAuthenticationForClients();
  }

}
