package com.souther.cloud.filter;

import com.souther.cloud.common.conf.WhiteListConfig;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author souther
 * @Date: 2020/12/22 11:53
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

  @Resource
  private WhiteListConfig whiteListConfig;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    ServerHttpRequest request = exchange.getRequest();
    //防止 OPTIONS 请求直接放行
    if (request.getMethod().equals(HttpMethod.OPTIONS)) {
      return chain.filter(exchange);
    }
    //白名单请求直接放行
    PathMatcher pathMatcher = new AntPathMatcher();
    for (String path : whiteListConfig.getUrls()) {
      if (pathMatcher.match("/**" + path, request.getPath().toString())) {
        return chain.filter(exchange);
      }
    }


    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
