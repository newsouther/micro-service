package com.souther.cloud.filter;

import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class WrapperResponseGlobalFilter implements GlobalFilter, Ordered {

  @Override
  public int getOrder() {
    //-1 is response write filter, must be called before that
    return -2;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    //获取response的 返回数据
    ServerHttpResponse originalResponse = exchange.getResponse();
    DataBufferFactory bufferFactory = originalResponse.bufferFactory();
    ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
      @Override
      public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
          Flux<? extends DataBuffer> fluxBody = Flux.from(body);
          return super.writeWith(fluxBody.map(dataBuffer -> {
            byte[] content = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(content);
            //释放掉内存
            DataBufferUtils.release(dataBuffer);
            //responseData就是下游系统返回的内容,可以查看修改
            String responseData = new String(content, Charset.forName("UTF-8"));
            log.info("");
            log.info("***********************************响应信息**********************************");
            log.info("响应内容:{}", responseData);
            log.info("****************************************************************************\n");
            byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
            return bufferFactory.wrap(uppedContent);
          }));
        } else {
          log.error("响应code异常:{}", getStatusCode());
        }
        return super.writeWith(body);
      }
    };
    return chain.filter(exchange.mutate().response(decoratedResponse).build());
  }
}
