package com.thirdstart.testing.rest.resthelper

import org.springframework.http.HttpEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * Wraps up common items needed to make a REST request and handles things like expanding
 * uri variables ('params') into tokenized URIs for the Spring RestTemplate.
 */
class SimpleRestRequest {

    /**
     * The type of object onto which responses should be mapped, like com.your.great.service.Contact
     */
    def type = null

    /**
     * A map of URI variables. This'll be used to expand the query string in getUri().
     */
    Map params = null

    /**
     * The path of the request, like '/thing'
     */
    String path

    /**
     * The service's root URL, like https://your.great.service
     */
    String serviceUrl

    /**
     * Any body to be passed in something like a POST or PUT.
     */
    Object body

    /**
     * Any headers we'd like to add to the request
     */
    Map<String, String> headers = [:]

    /**
     * The expanded URI used for the actual request, based on serviceUrl, path, and params. If your serviceUrl
     * is https://your.great.service, and path is /thing, and params is [foo: 'bar'], this'll result in a tokenized
     * uri such as https://your.great.service/thing?foo={foo}, allowing RestTemplate to fill in the actual parameter
     * values.
     */
    String getUri() {
        String uri = serviceUrl + path

        if (params) {
            params.eachWithIndex { Object k, v, Integer i ->
                uri += (i == 0 ? '?' : '&') + k + '={' + k + '}'
            }
        }

        return uri
    }

    /**
     * Returns a new HttpEntity with a content-type of application/json for use in RestTemplate exchange() operations.
     */
    HttpEntity getHttpEntity() {
        MultiValueMap<String, String> entityHeaders = new LinkedMultiValueMap<String, String>()
        entityHeaders.add("Content-Type", "application/json")

        headers.eachWithIndex { String k, String v, i ->
            entityHeaders.add(k, v)
        }

        return new HttpEntity(body, entityHeaders)
    }
}
