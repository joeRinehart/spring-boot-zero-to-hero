package com.thirdstart.spring.zerotohero.util.rest

import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class RestServiceHelper {

    String serviceUrl
    RestTemplate restTemplate

    def iterableTypeFor( Class iterableClass, Class memberClass ) {
        return new DynamicParameterizedTypeReference<Object>( iterableClass, memberClass ) {};
    }

    def listOf( Class memberClass ) {
        return iterableTypeFor( ArrayList, memberClass )
    }

    // Common boilerplate
    @SuppressWarnings("GroovyAssignabilityCheck")
    protected exchange(SimpleRestRequest simpleRestRequest, HttpMethod httpMethod ) {
        simpleRestRequest.serviceUrl = serviceUrl

        ResponseEntity responseEntity

        if ( simpleRestRequest.type == null && simpleRestRequest.params == null ) {
            responseEntity = restTemplate.getForEntity( simpleRestRequest.uri, null )
        } else if ( simpleRestRequest.params == null ) {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    httpMethod,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type
            )
        } else {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    httpMethod,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type,
                    simpleRestRequest.params
            )
        }

        return responseEntity
    }
    
    
    // GET boilerplate...

    def get(String path, Map params) {
        return get([path: path, params: params])
    }

    def get(String path) {
        return get([ path: path ])
    }

    def get(String path, Object type) {
        return get([ path: path, type: type ])
    }

    def get(Map params) {
        return get(new SimpleRestRequest(params))
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def get(SimpleRestRequest simpleRestRequest) {
        /*
        simpleRestRequest.serviceUrl = serviceUrl

        ResponseEntity responseEntity

        if ( simpleRestRequest.type == null && simpleRestRequest.params == null ) {
            responseEntity = restTemplate.getForEntity( simpleRestRequest.uri, null )
        } else if ( simpleRestRequest.params == null ) {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    HttpMethod.GET,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type
            )
        } else {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    HttpMethod.GET,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type,
                    simpleRestRequest.params
            )
        }

        return responseEntity
        */
        return exchange(simpleRestRequest, HttpMethod.GET)
    }

    // POST boilerplate...

    def post(String path, Object body, Object type) {
        return post([path: path, body: body, type: type])
    }

    def post(String path, Object body) {
        return post([path: path, body: body, type: body.class])
    }

    def post( Map params ) {
        return post(new SimpleRestRequest(params))
    }

    def post(SimpleRestRequest simpleRestRequest) {
        /*
        simpleRestRequest.serviceUrl = serviceUrl


        ResponseEntity responseEntity

        if ( simpleRestRequest.params == null ) {
            responseEntity = restTemplate.postForEntity(
                    simpleRestRequest.uri,
                    simpleRestRequest.body,
                    simpleRestRequest.type
            )
        } else {
            responseEntity = restTemplate.postForEntity(
                    simpleRestRequest.uri,
                    simpleRestRequest.body,
                    simpleRestRequest.type,
                    simpleRestRequest.params
            )
        }

        return responseEntity
        */
        return exchange(simpleRestRequest, HttpMethod.POST)
    }


    // PUT boilerplate...

    def put(String path, Object body, Object type) {
        return put([path: path, body: body, type: type])
    }

    def put(String path, Object body) {
        return put([path: path, body: body, type: body.class])
    }

    def put( Map params ) {
        return put(new SimpleRestRequest(params))
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def put(SimpleRestRequest simpleRestRequest) {
        /*
        simpleRestRequest.serviceUrl = serviceUrl

        ResponseEntity responseEntity

        if ( simpleRestRequest.params == null ) {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    HttpMethod.PUT,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type
            )
        } else {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    HttpMethod.PUT,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type,
                    simpleRestRequest.params
            )
        }

        return responseEntity
        */
        return exchange(simpleRestRequest, HttpMethod.PUT)
    }

    // DELETE boilerplate...

    def delete(String path) {
        return delete([ path: path, type: Object ])
    }

    def delete(String path, Object body) {
        return delete([path: path, body: body, type: body.class])
    }

    def delete(String path, Object body, Object type) {
        return delete([path: path, body: body, type: type])
    }

    def delete( Map params ) {
        return delete(new SimpleRestRequest(params))
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    def delete(SimpleRestRequest simpleRestRequest) {
        /*
        simpleRestRequest.serviceUrl = serviceUrl

        ResponseEntity responseEntity

        if ( simpleRestRequest.params == null ) {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    HttpMethod.DELETE,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type
            )
        } else {
            responseEntity = restTemplate.exchange(
                    simpleRestRequest.uri,
                    HttpMethod.DELETE,
                    simpleRestRequest.httpEntity,
                    simpleRestRequest.type,
                    simpleRestRequest.params
            )
        }

        return responseEntity
        */
        return exchange(simpleRestRequest, HttpMethod.DELETE)
    }
    
}

