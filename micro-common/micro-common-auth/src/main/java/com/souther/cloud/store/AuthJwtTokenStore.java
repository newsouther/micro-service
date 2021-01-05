package com.souther.cloud.store;

import com.souther.cloud.converter.CustomUserAuthenticationConverter;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @Description: token配置
 * @Author souther
 * @Date: 2020/12/26 14:42
 */
@Configuration
public class AuthJwtTokenStore {

  @Bean("keyProp")
  public KeyProperties keyProperties() {
    return new KeyProperties();
  }

  @Resource(name = "keyProp")
  private KeyProperties keyProperties;

  @Bean
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }

  /**
   * JWT内容增强
   *
   * @return
   */
  @Bean
  @Order(1)
  public TokenEnhancer tokenEnhancer() {
    return (accessToken, authentication) -> {
      Map<String, Object> map = new HashMap<>(0);
      // todo 塞入额外信息
      map.put("id","测试");
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
      return accessToken;
    };
  }

  @Bean
  @Order(2)
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    KeyPair keyPair = new KeyStoreKeyFactory
        (keyProperties.getKeyStore().getLocation(), keyProperties.getKeyStore().getSecret().toCharArray())
        .getKeyPair(keyProperties.getKeyStore().getAlias());
    converter.setKeyPair(keyPair);
    DefaultAccessTokenConverter tokenConverter = (DefaultAccessTokenConverter)converter.getAccessTokenConverter();
    tokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
    return converter;
  }
}
