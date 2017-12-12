package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.jwt.RuntimeJwtHelper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = ZeroToHeroConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbstractSecuredRestConfigurationSpecSpec extends AbstractSecuredRestConfigurationSpec {

    def "We can create and decrypt JWTs via RuntimeJwtHelper"() {
        when:
        def token = runtimeJwtHelper.createToken(
                'Joe',
                [
                    roles: ['ROLE_USER', 'ROLE_ADMIN']
                ]
        )

        then:
        token.size()

        when:
        Jws<Claims> jws = runtimeJwtHelper.parseToken(token)
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
        Jws<Claims> jws = runtimeJwtHelper.parseToken(token)
        Claims claims = jws.body

        then:

        claims.sub == 'test_user'
        claims.get('authorities').size() == 1
    }

    def "We can claim to be a certain user and the default RestServiceHelpers reflect this in their header maps"() {
        expect:
        service.headers[RuntimeJwtHelper.DEFAULT_AUTHORIZATION_HEADER] == null
        management.headers[RuntimeJwtHelper.DEFAULT_AUTHORIZATION_HEADER] == null

        when:
        String token = authenticateAs('test_user')

        then:
        service.headers[RuntimeJwtHelper.DEFAULT_AUTHORIZATION_HEADER] == token
        management.headers[RuntimeJwtHelper.DEFAULT_AUTHORIZATION_HEADER] == token
    }

}
