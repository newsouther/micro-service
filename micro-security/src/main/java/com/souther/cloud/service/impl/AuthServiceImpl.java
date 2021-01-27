package com.souther.cloud.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.souther.cloud.service.AuthService;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author souther
 * @Date: 2021/1/9 14:36
 */
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private KeyPair keyPair;

  @Override
  public Map<String, Object> getKey() {
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAKey key = new RSAKey.Builder(publicKey).build();
    return new JWKSet(key).toJSONObject();
  }

  @Override
  public String getJwt() {
    JWSSigner jwsSigner = new RSASSASigner(keyPair.getPrivate());
    JWSHeader jwsHeader = new JWSHeader
        .Builder(JWSAlgorithm.RS256)
        .type(JOSEObjectType.JWT).build();
    //参数
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .issuer("admin")
        .subject("sub")
        .claim("authorities", Arrays.asList("1"))
        .claim("scope", Arrays.asList("all"))
        .expirationTime(new Date(System.currentTimeMillis() + 604800 * 1000))
        .jwtID("123")
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
