package com.thirdstart.spring.zerotohero

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification


@TestPropertySource(properties = ["management.port=0","test.protocol=http","test.host=localhost"])
abstract class AbstractRestConfigurationSpec extends Specification {

    @LocalServerPort
    Integer port

    @Value('${local.management.port}')
    Integer managementPort

    @Autowired
    TestRestTemplate testRestTemplate

    @Value('${test.protocol}')
    String protocol

    @Value('${test.host}')
    String host

    def simpleRequest(Map params) {
        return simpleRequest(new SimpleRestRequest(params))
    }

    def simpleRequest(SimpleRestRequest simpleRestRequest) {
        simpleRestRequest.port = simpleRestRequest.port ?: port
        simpleRestRequest.type = simpleRestRequest.type ?: Map

        ResponseEntity responseEntity

        if ( simpleRestRequest.uriVariables == null ) {
            testRestTemplate.getForEntity(
                    simpleRestRequest.uri,
                    simpleRestRequest.type
            )
        } else {
            testRestTemplate.getForEntity(
                    simpleRestRequest.uri,
                    simpleRestRequest.type,
                    simpleRestRequest.uriVariables
            )
        }
    }
}


class SimpleRestRequest {
    Class type = null
    Map uriVariables = null
    Integer port
    String path
    String protocol = 'http'
    String host = 'localhost'

    String getUri() {
        String uri = protocol + '://' + host + ':' + port + path

        if ( uriVariables ) {
            uriVariables.eachWithIndex { k, v, i ->
                uri += (i == 0 ? '?' : '&') + k + '=' + v.toString()
            }
        }

        return uri
    }

}
