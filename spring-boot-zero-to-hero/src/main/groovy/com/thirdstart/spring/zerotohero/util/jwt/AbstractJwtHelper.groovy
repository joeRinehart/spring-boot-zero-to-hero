package com.thirdstart.spring.zerotohero.util.jwt

import io.jsonwebtoken.SignatureAlgorithm

import java.security.Key

abstract class AbstractJwtHelper {

    /**
     * Length of keys in bytes, defaulting to 512
     */
    Integer keyLength = 512

    /**
     * Algorithm name for jjwt, defaulting to 'RSA'
     */
    String algorithm = 'RSA'

    /**
     * Signature algorithm, defaulting to SignatureAlgorithm.RS256
     */
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256

    /**
     * Key to use. If using defaults, this'll be a private key for JwtCreator and a public key for JwtParser.
     */
    Key key

}
