package com.thirdstart.spring.zerotohero.other

import com.thirdstart.spring.zerotohero.ApplicationConfiguration
import com.thirdstart.spring.zerotohero.util.jwt.JwtParser
import com.thirdstart.testing.rest.abstractspecs.AbstractSecuredRestControllerSpec
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = ApplicationConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbstractSecuredRestControllerSpecSpec extends AbstractSecuredRestControllerSpec {

    @Value('${jwt.headerName}')
    String jwtHeaderName

    @Autowired
    JwtParser jwtParser

    def "We can create and decrypt JWTs via RuntimeJwtHelper"() {
        when:
        def token = jwtCreator.createToken(
                'Joe',
                [
                    roles: ['ROLE_USER', 'ROLE_ADMIN']
                ]
        )

        then:
        token.size()

        when:
        Jws<Claims> jws = jwtParser.parseToken(token)
        Claims claims = jws.body

        then:

        claims.sub == 'Joe'
        claims.get('roles').size() == 2
        claims.get('roles').contains('ROLE_ADMIN')
        claims.get('roles').contains('ROLE_USER')
    }

    def "We can create a token based on a configured test user"() {
        when:
        def token = createJwtForUser('test_user')

        then:
        token != null

        when:
        Jws<Claims> jws = jwtParser.parseToken(token)
        Claims claims = jws.body

        then:

        claims.sub == 'test_user'
        claims.get('authorities').size() == 1
    }

    def "We can claim to be a certain user and the default RestServiceHelpers reflect this in their header maps"() {
        expect:
        service.headers[jwtHeaderName] == null
        management.headers[jwtHeaderName] == null

        when:
        String token = withUser('test_user')

        then:
        service.headers[jwtHeaderName] == token
        management.headers[jwtHeaderName] == token
    }

}
