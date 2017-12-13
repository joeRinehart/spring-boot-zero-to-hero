package com.thirdstart.spring.zerotohero.util.jwt

import groovy.util.logging.Log4j
import io.jsonwebtoken.Jwts

@Log4j
/**
 * Can create JWT tokens using the assigned key
 */
class JwtCreator extends AbstractJwtHelper {

    static final String DEFAULT_AUTHORIZATION_HEADER = 'Authorization'

    String createToken(String subject, Map<String, Object> claims) {
        Jwts.builder()
            .setSubject(subject)
            .addClaims(claims)
            .signWith(signatureAlgorithm, key)
            .compact()
    }
}
