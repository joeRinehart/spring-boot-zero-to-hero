package com.thirdstart.spring.zerotohero.util.rsa

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * Quick and dirty utility to parse base 64 encoded representations of public/private keys
 * to PrivateKey and PublicKey instances
 */
class RsaKeyParser {

    static KeyFactory kf = KeyFactory.getInstance("RSA")

    static PrivateKey toPrivateKey(String privateKey) {
        return kf.generatePrivate(
            new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(privateKey)
            )
        )
    }

    static PublicKey toPublicKey(String publicKey) {
        return kf.generatePublic(
            new X509EncodedKeySpec(
                Base64.getDecoder().decode(publicKey)
            )
        )
    }

}
