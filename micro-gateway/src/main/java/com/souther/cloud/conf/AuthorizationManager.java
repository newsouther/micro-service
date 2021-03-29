package com.souther.cloud.conf;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.souther.cloud.constant.Constant;
import com.souther.cloud.constant.RedisKeyEnum;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
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

    // 1. 对应跨域的预检请求直接放行
    if (request.getMethod() == HttpMethod.OPTIONS) {
      return Mono.just(new AuthorizationDecision(true));
    }

    // 2. token为空拒绝访问
    String token = request.getHeaders().getFirst(Constant.USER_TOKEN_HEADER);
    if (StrUtil.isBlank(token)) {
      return Mono.just(new AuthorizationDecision(false));
    }

    //从Redis中获取当前路径可访问角色列表
    //获取hash中指定字段的值： 示例结构：{uri：[role1，role2]}
//    Object obj = redisTemplate.opsForHash().get("key",path);
//    List<String> authorities = Convert.toList(String.class, obj);
//    List<String> authorities = Arrays.asList("ROLE_1");
//    authorities = authorities.stream().map(i -> i = "ROLE_" + i).collect(
//        Collectors.toList());

    // 从缓存取资源权限角色关系列表
    Map<Object, Object> permissionRolesMap = redisTemplate.opsForHash()
        .entries(RedisKeyEnum.RESOURCE_ROLES_KEY.getKey());
    Iterator<Object> iterator = permissionRolesMap.keySet().iterator();

    // 请求路径匹配到的资源需要的角色权限集合authorities统计 【这里适配通配符】
    Set<String> authorities = new HashSet<>();
    PathMatcher pathMatcher = new AntPathMatcher();
    while (iterator.hasNext()) {
      String pattern = (String) iterator.next();
      if (pathMatcher.match(pattern, path)) {
        authorities.addAll(Convert.toList(String.class, permissionRolesMap.get(pattern)));
      }
    }

    //认证通过且角色匹配的用户可访问当前路径
    return mono
        .filter(Authentication::isAuthenticated)
        .flatMapIterable(Authentication::getAuthorities)
        .map(GrantedAuthority::getAuthority)
//        .any(authorities::contains)
        .any(roleId -> {
          // todo 没有加入白名单、也不需要权限控制的路径，访问时，一样是没有权限。
          log.info("访问路径：{}", path);
          log.info("用户角色roleId：{}", roleId);
          log.info("资源需要权限authorities：{}", authorities);
          return authorities.contains(roleId);
        })
        .map(AuthorizationDecision::new)
        .defaultIfEmpty(new AuthorizationDecision(false));
  }

}
