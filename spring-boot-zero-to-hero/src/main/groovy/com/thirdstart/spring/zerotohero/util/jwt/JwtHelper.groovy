package com.thirdstart.spring.zerotohero.util.jwt

import groovy.util.logging.Log4j
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

@Log4j
/**
 * Helps do JWT work for tests by creating a runtime public/private keypair and creating/deserializing
 * JWTs
 */
class JwtHelper {

    static final String DEFAULT_AUTHORIZATION_HEADER = 'Authorization'

    String privateKeyString = 'MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEA1SZMyHGb6qUh0lJUDmY9kqh8anCLActmPs4S8bWQSfzl1xCagoSKr7Ls8QG+JoSiuy49QUHpxs+GeI8oFFiQhQIDAQABAkEApXGuBaORRydXwj2e+vJG9rAKqj+i1gi/2x3vPGgWkCXt9psVWaE0gbzP820y2q/P/oHDPXPraLNt7X0w0G0kAQIhAPny/oygwSYbBDkl0A0kdLmkqWJPcsPNfZXp5nUH99eFAiEA2k8/iJEDcPdVZsy+TdewnGpq5hIG0u8YQ3+2NdIYpQECIB/w7JSeCnDLLQv8iAzV5tC+eSLmEj1xaiYZQOkdvo9dAiEAqmDnKOJkWSnvUQfCDmajw6+aNm0jPkS0HxnxTxflfAECIQD1RyzjEmhIxgbr1fSxzBbSvqakzdbq04VozB92piQANQ=='
    String publicKeyString = 'MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANUmTMhxm+qlIdJSVA5mPZKofGpwiwHLZj7OEvG1kEn85dcQmoKEiq+y7PEBviaEorsuPUFB6cbPhniPKBRYkIUCAwEAAQ=='

    Integer keyLength = 512
    String algorithm = 'RSA'
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256

    KeyPair _keyPair
    KeyPair getKeyPair() {
        _keyPair = _keyPair ?: createKeyPair()

        PrivateKey privateKey = _keyPair.private
        PublicKey publicKey = _keyPair.public

        log.info("PRIVATE KEY:\n\n${Base64.getEncoder().encodeToString(privateKey.encoded)}")
        log.info("PUBLIC KEY:\n\n${Base64.getEncoder().encodeToString(publicKey.encoded)}")

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
