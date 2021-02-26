package com.souther.cloud.conf;

import cn.hutool.core.util.ArrayUtil;
import com.souther.cloud.component.CustomServerAccessDeniedHandler;
import com.souther.cloud.component.CustomServerAuthenticationEntryPoint;
import com.souther.cloud.constant.Constant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @Description: 资源服务器
 * @Author souther
 * @Date: 2021/1/5 11:12
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

  private CustomServerAccessDeniedHandler customServerAccessDeniedHandler;
  private CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;
  private AuthorizationManager authorizationManager;
  private WhiteListConfig whiteListConfig;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http.oauth2ResourceServer().jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
    // 自定义处理JWT请求头过期或签名错误的结果
    http.oauth2ResourceServer().authenticationEntryPoint(customServerAuthenticationEntryPoint);
    http.authorizeExchange()
        .pathMatchers(ArrayUtil.toArray(whiteListConfig.getUrls(),String.class)).permitAll()
        .anyExchange().access(authorizationManager)
        .and()
        .exceptionHandling()
        .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
        .authenticationEntryPoint(customServerAuthenticationEntryPoint) //处理未认证
        .and().csrf().disable();
    return http.build();
  }

  /**
   * @linkhttps://blog.csdn.net/qq_24230139/article/details/105091273
   * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
   * 需要把jwt的Claim中的authorities加入
   * 方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
   */
  @Bean
  public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix(Constant.AUTHORITY_PREFIX);
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(Constant.AUTHORITY_CLAIM_NAME);

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }

}
