package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.helpers.RestServiceHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

abstract class AbstractRestConfigurationSpec extends Specification {

    @Value('${server.protocol}')
    String serverProtocol

    @Value('${server.host}')
    String serverHost

    @LocalServerPort
    Integer serverPort

    @Value('${management.protocol}')
    String managementProtocol

    @Value('${management.host}')
    String managementHost

    @Value('${local.management.port}')
    Integer managementPort

    @Autowired
    TestRestTemplate testRestTemplate

    RestServiceHelper getService() {
        return new RestServiceHelper(
            testRestTemplate: testRestTemplate,
            serviceUrl: serverProtocol + '://' + serverHost + ':' + serverPort
        )
    }

    RestServiceHelper getManagement() {
        return new RestServiceHelper(
            testRestTemplate: testRestTemplate,
            serviceUrl: managementProtocol + '://' + managementHost + ':' + managementPort
        )
    }



}


