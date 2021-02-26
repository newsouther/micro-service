package com.souther.cloud.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/27 11:23
 */
@Component
public class JwtUtils {

  @Autowired
  private KeyPair keyPairService;

  private static KeyPair keyPair;

  @PostConstruct
  public void init() {
    keyPair = keyPairService;
  }

  public static String getJwt(Long userId) {
    JWSSigner jwsSigner = new RSASSASigner(keyPair.getPrivate());
    JWSHeader jwsHeader = new JWSHeader
        .Builder(JWSAlgorithm.RS256)
        .type(JOSEObjectType.JWT).build();
    //参数
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//        .issuer("admin")
//        .subject("sub")
        .claim("userId", userId)
        .claim("authorities", Arrays.asList("1"))
        .claim("scope", Arrays.asList("all"))
        .expirationTime(new Date(System.currentTimeMillis() + 604800 * 1000))
        .jwtID(IdWorker.getIdStr())
        .build();
    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(jwsHeader, payload);
    try {
      jwsObject.sign(jwsSigner);
    } catch (JOSEException e) {
      e.printStackTrace();
    }
    //base64
    String token = jwsObject.serialize();
    return token;
  }

}
