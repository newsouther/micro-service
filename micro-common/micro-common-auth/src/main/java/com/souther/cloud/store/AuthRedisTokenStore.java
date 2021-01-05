package com.souther.cloud.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器使用Redis存取令牌 注意: 需要配置redis参数
 */
@Configuration
public class AuthRedisTokenStore {

  @Bean
  public TokenStore tokenStore(RedisConnectionFactory connectionFactory,
      RedisSerializer<Object> redisValueSerializer) {
    return new CustomRedisTokenStore(connectionFactory, redisValueSerializer);
  }

}
