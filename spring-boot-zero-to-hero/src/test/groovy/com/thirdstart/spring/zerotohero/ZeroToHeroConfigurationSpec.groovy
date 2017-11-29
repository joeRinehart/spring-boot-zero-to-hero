package com.thirdstart.spring.zerotohero

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(classes = ZeroToHeroConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZeroToHeroConfigurationSpec extends AbstractRestConfigurationSpec {

    def "Should return 200 when GETing /hello-world our service"() {
        when:
        ResponseEntity response = service.get( '/hello-world' )

        then:
        response.statusCode == HttpStatus.OK
    }

    def "Should return 200 when GETing /info from management interface"() {
        when:
        ResponseEntity response = management.get( '/info' )

        then:
        response.statusCode == HttpStatus.OK
    }
}
