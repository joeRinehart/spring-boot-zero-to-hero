package com.thirdstart.spring.zerotohero

import com.github.tomakehurst.wiremock.junit.WireMockClassRule
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import groovy.json.JsonBuilder
import org.junit.ClassRule
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared

import javax.annotation.Resource

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig

/**
 * Extends the convenience AbstractRestConfigurationSpec with properties and methods to drive a Wiremock-based
 * mock jwt-based authorization server
 */
class AbstractSecuredRestConfigurationSpec extends AbstractRestConfigurationSpec {

    @Resource(name='testUsers')
    List testUsers

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



}
