package com.thirdstart.spring.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

import java.security.KeyPair
import java.security.KeyPairGenerator

/**
 * Helps do JWT work for tests by creating a runtime public/private keypair and creating/deserializing
 * JWTs
 */
class RuntimeJwtHelper {

    static final String DEFAULT_AUTHORIZATION_HEADER = 'Authorization'

    Integer keyLength = 512
    String algorithm = 'RSA'
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256

    KeyPair _keyPair
    KeyPair getKeyPair() {
        _keyPair = _keyPair ?: createKeyPair()
        return _keyPair
    }

    String createToken(String subject, Map<String, Object> claims) {
        Jwts.builder()
            .setSubject(subject)
            .addClaims(claims)
            .signWith(signatureAlgorithm, keyPair.private)
            .compact()
    }

    Jws<Claims> parseToken(String token) {
        return Jwts.parser()
            .setSigningKey(keyPair.public)
            .parseClaimsJws(token)
    }

    protected createKeyPair() {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(keyLength)
        _keyPair = kpg.generateKeyPair()
    }
}
