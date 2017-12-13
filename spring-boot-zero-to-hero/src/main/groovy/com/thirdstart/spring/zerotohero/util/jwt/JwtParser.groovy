package com.thirdstart.spring.zerotohero.util.jwt

import groovy.util.logging.Log4j
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts

@Log4j
/**
 * Interprets incoming JWT tokens using a configured key
 */
class JwtParser extends AbstractJwtHelper {

    Jws<Claims> parseToken(String token) {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
    }

}
