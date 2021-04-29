package com.souther.cloud.common.conf;

import java.security.KeyPair;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/9 14:43
 */
@Slf4j
@Configuration
public class KeyConf {

  @Bean("keyProp")
  public KeyProperties keyProperties() {
    return new KeyProperties();
  }

  @Resource(name = "keyProp")
  private KeyProperties keyProperties;

  /**
   * 从classpath下的密钥库中获取密钥对(公钥+私钥)
   */
  @Bean
  public KeyPair keyPair() {
    KeyPair keyPair = new KeyStoreKeyFactory
        (keyProperties.getKeyStore().getLocation(),
            keyProperties.getKeyStore().getSecret().toCharArray())
        .getKeyPair(keyProperties.getKeyStore().getAlias());
    return keyPair;
  }

}
