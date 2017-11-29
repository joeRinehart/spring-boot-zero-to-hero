package com.thirdstart.spring.zerotohero.helpers

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

class RestServiceHelper {

    String serviceUrl
    TestRestTemplate testRestTemplate


    def get(String path) {
        return get([ path: path ])
    }

    def get(Map params) {
        return get(new SimpleRestRequest(params))
    }

    def get(SimpleRestRequest simpleRestRequest) {
        simpleRestRequest.serviceUrl = serviceUrl

        ResponseEntity responseEntity

        if ( simpleRestRequest.params == null ) {
            responseEntity = testRestTemplate.getForEntity(
                    simpleRestRequest.uri,
                    simpleRestRequest.type
            )
        } else {
            responseEntity = testRestTemplate.getForEntity(
                    simpleRestRequest.uri,
                    simpleRestRequest.type,
                    simpleRestRequest.params
            )
        }

        return responseEntity
    }

}
