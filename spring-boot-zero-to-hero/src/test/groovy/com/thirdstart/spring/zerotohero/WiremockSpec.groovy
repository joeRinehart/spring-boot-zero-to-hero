package com.thirdstart.spring.zerotohero

import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import com.thirdstart.spring.zerotohero.util.rest.RestServiceHelper
import org.junit.Rule
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * Checks that we can use wiremock to create access tokens
 */
@SpringBootTest(classes = ZeroToHeroConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WiremockSpec extends AbstractSecuredRestConfigurationSpec {

    def "We can mock a changing principal with Wiremock"() {
        setup:
        stubPrincipal('admin_user')

        RestServiceHelper rsh = new RestServiceHelper( restTemplate: new RestTemplate(), serviceUrl: "http://localhost:" + wireMockRule.port())

        when: "we get /principal"
        ResponseEntity<Map> responseEntity = rsh.get('/principal', Map)

        then: "we receive the principal"
        responseEntity.body.username == 'admin_user'

        when: "we restub and get /principal again"
        stubPrincipal('test_user')
        responseEntity = rsh.get('/principal', Map)

        then: "we receive a different principal"
        responseEntity.body.username == 'test_user'
    }
}
