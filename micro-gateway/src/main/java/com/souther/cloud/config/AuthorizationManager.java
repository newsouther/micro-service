package com.souther.cloud.config;

import cn.hutool.core.util.StrUtil;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/8 11:01
 */
@Slf4j
@AllArgsConstructor
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

  private RedisTemplate<String, Object> redisTemplate;

  @Override
  public Mono<AuthorizationDecision> check(Mono<Authentication> mono,
      AuthorizationContext authorizationContext) {

    ServerHttpRequest request = authorizationContext.getExchange().getRequest();

    String path = request.getURI().getPath();
//    PathMatcher pathMatcher = new AntPathMatcher();

    // 1. 对应跨域的预检请求直接放行
    if (request.getMethod() == HttpMethod.OPTIONS) {
      return Mono.just(new AuthorizationDecision(true));
    }

    // 2. token为空拒绝访问
    String token = request.getHeaders().getFirst("Authorization");
    if (StrUtil.isBlank(token)) {
      return Mono.just(new AuthorizationDecision(false));
    }

    //从Redis中获取当前路径可访问角色列表
    URI uri = authorizationContext.getExchange().getRequest().getURI();
    //获取hash中指定字段的值： 示例结构：{uri：[role1，role2]}
    Object obj = redisTemplate.opsForHash().get(path, uri.getPath());
//    List<String> authorities = Convert.toList(String.class, obj);
    List<String> authorities = Arrays.asList("1");
    authorities = authorities.stream().map(i -> i = "ROLE_" + i).collect(
        Collectors.toList());

    //认证通过且角色匹配的用户可访问当前路径
    List<String> finalAuthorities = authorities;
    return mono
        .filter(Authentication::isAuthenticated)
        .flatMapIterable(Authentication::getAuthorities)
        .map(GrantedAuthority::getAuthority)
//        .any(authorities::contains)
        .any(roleId -> {
          log.info("访问路径：{}", path);
          log.info("用户角色roleId：{}", roleId);
          log.info("资源需要权限authorities：{}", finalAuthorities);
          return finalAuthorities.contains(roleId);
        })
        .map(AuthorizationDecision::new)
        .defaultIfEmpty(new AuthorizationDecision(false));
  }

}
