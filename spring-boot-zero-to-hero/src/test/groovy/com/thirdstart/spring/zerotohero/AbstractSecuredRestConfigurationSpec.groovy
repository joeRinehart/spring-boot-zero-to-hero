package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.util.jwt.JwtHelper
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.Resource

/**
 * Extends the convenience AbstractRestConfigurationSpec with helpers for testing a JWT-driven API
 */
class AbstractSecuredRestConfigurationSpec extends AbstractRestConfigurationSpec {

    @Autowired
    JwtHelper runtimeJwtHelper

    @Resource(name='testUsers')
    List testUsers

    String createJwtForUser(String username) {
        def principal = testUsers.users.first().find{
            it.username == username
        }

        if ( !principal ) {
            throw new RuntimeException("There's no user with username ${username} in your test-users.yml!")
        }

        return runtimeJwtHelper.createToken(principal.username, [ authorities: principal.authorities ])
    }

    String withUser(String username, String authorizationHeaderName = JwtHelper.DEFAULT_AUTHORIZATION_HEADER) {
        String token = createJwtForUser(username)

        defaultHeaders[authorizationHeaderName] = token

        return token
    }

}
