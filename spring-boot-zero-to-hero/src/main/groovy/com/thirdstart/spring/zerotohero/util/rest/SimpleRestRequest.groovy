package com.thirdstart.spring.zerotohero.util.rest

import org.springframework.http.HttpEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class SimpleRestRequest {
    def type = null
    Map params = null
    String path
    String serviceUrl
    Object body

    String getUri() {
        String uri = serviceUrl + path

        if (params) {
            params.eachWithIndex { Object k, v, Integer i ->
                uri += (i == 0 ? '?' : '&') + k + '={' + k + '}'
            }
        }

        return uri
    }

    HttpEntity getHttpEntity() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>()
        headers.add("HeaderName", "value")
        headers.add("Content-Type", "application/json")
        return new HttpEntity(body, headers)
    }
}
