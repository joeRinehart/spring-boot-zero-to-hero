package com.thirdstart.testing.rest.abstractspecs

import com.thirdstart.spring.zerotohero.util.jwt.JwtCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import javax.annotation.Resource

/**
 * Extends the convenience AbstractRestConfigurationSpec with helpers for testing a JWT-driven API
 */
class AbstractSecuredRestControllerSpec extends AbstractRestControllerSpec {

    @Autowired
    JwtCreator jwtCreator

    @Value('${jwt.headerName}')
    String jwtHeaderName

    @Resource(name='testUsers')
    List testUsers

    String createJwtForUser(String username) {
        def principal = testUsers.users.first().find{
            it.username == username
        }

        if ( !principal ) {
            throw new RuntimeException("There's no user with username ${username} in your test-users.yml!")
        }

        return jwtCreator.createToken(principal.username, [ authorities: principal.authorities ])
    }

    String withUser(String username, String authorizationHeaderName = jwtHeaderName) {
        String token = createJwtForUser(username)

        defaultHeaders[authorizationHeaderName] = token

        return token
    }

}
