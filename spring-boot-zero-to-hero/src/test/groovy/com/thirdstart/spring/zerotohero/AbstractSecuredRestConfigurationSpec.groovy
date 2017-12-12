package com.thirdstart.spring.zerotohero

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import com.thirdstart.spring.jwt.RuntimeJwtHelper
import groovy.json.JsonBuilder
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.Resource

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * Extends the convenience AbstractRestConfigurationSpec with helpers for testing a JWT-driven API
 */
class AbstractSecuredRestConfigurationSpec extends AbstractRestConfigurationSpec {

    @Autowired
    RuntimeJwtHelper runtimeJwtHelper

    @Resource(name='testUsers')
    List testUsers

    String createJwtForUser(String username) {
        def principal = testUsers.users.first().find{
            it.username == username
        }

        if ( !principal ) {
            throw new RuntimeException("There's no user with username ${principalName} in your test-users.yml!")
        }

        return runtimeJwtHelper.createToken(principal.username, [ authorities: principal.authorities ])
    }

    String authenticateAs(String username, String authorizationHeaderName = RuntimeJwtHelper.DEFAULT_AUTHORIZATION_HEADER) {
        String token = createJwtForUser(username)

        defaultHeaders[authorizationHeaderName] = token

        return token
    }
    /*
    @Rule
    WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort())

    WireMockGroovy wireMock

    def setup() {
        wireMock = new WireMockGroovy(wireMockRule.port())
    }

    void stubPrincipal(String principalName) {
        def principal = testUsers.users.first().find{
            it.username == principalName
        }

        if ( !principal ) {
            throw new RuntimeException("There's no user with username ${principalName} in your test-users.yml!")
        }

        wireMock.stub {
            request {
                method "GET"
                url "/principal"
            }
            response {
                status 200
                body new JsonBuilder(principal).toString()
                headers { "Authorization" "bearerTokenContent" }
                headers { "Content-Type" "application/json" }
            }
        }

    }
    */



}
