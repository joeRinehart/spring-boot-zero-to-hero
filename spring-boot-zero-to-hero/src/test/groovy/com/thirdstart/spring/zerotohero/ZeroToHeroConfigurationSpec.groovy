package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.domain.Greeting
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = ZeroToHeroConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZeroToHeroConfigurationSpec extends AbstractRestConfigurationSpec {

    def "Should be able to map a response entity to a Greeting"() {
        setup:
        String randomName = UUID.randomUUID().toString()

        when:
        ResponseEntity<Greeting> entity = simpleRequest(
            type: Greeting,
            path: '/hello-world',
            uriVariables: [
                name: randomName
            ]
        )

        then:
        entity.body instanceof Greeting
        entity.body.content == 'Hello, ' + randomName + '!'
    }

    def "Should return 200 when GETing /hello-world"() {
        when:
        ResponseEntity entity = simpleRequest(
            path: '/hello-world'
        )

        then:
        entity.statusCode == HttpStatus.OK
    }

    def "Should return 200 when GETing /info from management interface"() {
        when:
        ResponseEntity entity = simpleRequest(
            path: '/info',
            port: managementPort
        )

        then:
        entity.statusCode == HttpStatus.OK
    }
}
