package com.thirdstart.spring.zerotohero.util.rest

import com.thirdstart.spring.zerotohero.util.rest.exceptionhandling.ApiErrorInformation
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

/**
 * Wrapper for a Spring RestTemplate that simplifies common REST operations.
 */
class RestServiceHelper {

    // PROPERTIES

    /**
     * The URL for the service this helper should target, such as http://your.great.service/
     */
    String serviceUrl

    /**
     * The underlying RestTemplate. You'd want to wire one in or construct your own RestServiceHelpers on the fly.
     */
    RestTemplate restTemplate

    // PUBLIC METHODS

    /**
     * Fairly specific to our helper classes: creates an expected response type of ApiErrorInformation<memberClass>,
     * allowing you to receive an ApiErrorInformation instance that contains a strongly-typed 'subject' property
     */
    def apiErrorInformationFor( Class memberClass ) {
        return new DynamicParameterizedTypeReference<Object>( ApiErrorInformation, memberClass ) {};
    }

    /**
     * Replaces having to manually create ParameterizedTypeReference subclasses anytime you want to get a list
     * of something other than a generic Object back from a service.
     *
     * @param memberClass The type you're expecting to receive, like com.your.great.service.domain.Contact.
     * @return A ParameterizedTypeReference subclass that represents an ArrayList of your memberClass
     */
    def listOf( Class memberClass ) {
        return iterableTypeFor( ArrayList, memberClass )
    }

    /**
     * Replaces having to manually create ParameterizedTypeReference subclasses anytime you want to get an iterable
     * of something other than a generic Object back from a service.
     *
     * @param iterableClass The type of iterable you expect, such as java.util.ArrayList
     * @param memberClass The type you're expecting to receive, like com.your.great.service.domain.Contact.
     * @return A ParameterizedTypeReference subclass that represents an parameterizableClass of your memberClass
     */
    def iterableTypeFor( Class iterableClass, Class memberClass ) {
        return new DynamicParameterizedTypeReference<Object>( iterableClass, memberClass ) {};
    }


    // GET boilerplate...

    /**
     * Does a GET to the stated path.
     *
     * @param path
     * @return
     */
    ResponseEntity get(String path) {
        return get([ path: path ])
    }

    /**
     * Does a GET request to the stated path, expanding the path to include any items in params in its
     * query string. For example, get('/thing', [ foo: 'bar' ]) will do a GET to /thing?foo=bar.
     *
     * @param path
     * @param params
     * @return
     */
    ResponseEntity get(String path, Map params) {
        return get([path: path, params: params])
    }

    /**
     * Does a GET request to the stated path and maps the response JSON to an instance of the type
     * passed. For example, get('/thing/1', Thing) will result in responseEntity.body being an instance
     * of Thing.
     *
     * @param path
     * @param type
     * @return
     */
    ResponseEntity get(String path, Object type) {
        return get([ path: path, type: type ])
    }

    /**
     * Does a GET request using a SimpleRestRequest, initializing its properties with whatever is passed
     * as params.
     *
     * @param params
     * @return
     */
    ResponseEntity get(Map params) {
        return get(new SimpleRestRequest(params))
    }

    /**
     * Does a GET request using a SimpleRestRequest.
     *
     * @param params
     * @return
     */
    ResponseEntity get(SimpleRestRequest simpleRestRequest) {
        return exchange(simpleRestRequest, HttpMethod.GET)
    }

    // POST boilerplate...

    /**
     * Does a POST request to the stated path and maps the response JSON to an instance of the type
     * passed. For example, post('/thing', Thing) will result in responseEntity.body being an instance
     * of Thing.
     *
     * @param path
     * @param type
     * @return
     */
    ResponseEntity post(String path, Object body) {
        return post(path, body, body.class)
    }

    /**
     * Does a POST request to the stated path and maps the response JSON to an instance of the type
     * passed. For example, post('/thing', Thing, ThingResponse) will result in responseEntity.body
     * being an instance of ThingResponse.
     *
     * @param path
     * @param type
     * @return
     */
    ResponseEntity post(String path, Object body, Object type) {
        return post([path: path, body: body, type: type])
    }

    /**
     * Does a POST request using a SimpleRestRequest, initializing its properties with whatever is passed
     * as params.
     *
     * @param params
     * @return
     */
    ResponseEntity post( Map params ) {
        return post(new SimpleRestRequest(params))
    }

    /**
     * Does a POST request using a SimpleRestRequest.
     *
     * @param params
     * @return
     */
    ResponseEntity post(SimpleRestRequest simpleRestRequest) {
        return exchange(simpleRestRequest, HttpMethod.POST)
    }


    // PUT boilerplate...

    /**
     * Does a PUT request to the stated path and maps the response JSON to an instance of the type
     * passed. For example, post('/thing/1', Thing) will result in responseEntity.body being an instance
     * of Thing.
     *
     * @param path
     * @param type
     * @return
     */
    ResponseEntity put(String path, Object body) {
        return put(path, body, body.class)
    }

    /**
     * Does a PUT request to the stated path and maps the response JSON to an instance of the type
     * passed. For example, post('/thing/1', Thing, ThingResponse) will result in responseEntity.body
     * being an instance of ThingResponse.
     *
     * @param path
     * @param type
     * @return
     */
    ResponseEntity put(String path, Object body, Object type) {
        return put([path: path, body: body, type: type])
    }

    /**
     * Does a PUT request using a SimpleRestRequest, initializing its properties with whatever is passed
     * as params.
     *
     * @param params
     * @return
     */
    ResponseEntity put( Map params ) {
        return put(new SimpleRestRequest(params))
    }

    /**
     * Does a PUT request using a SimpleRestRequest.
     *
     * @param params
     * @return
     */
    ResponseEntity put(SimpleRestRequest simpleRestRequest) {
        return exchange(simpleRestRequest, HttpMethod.PUT)
    }

    // DELETE boilerplate...

    /**
     * Does a DELETE to the stated path and doesn't care about the response.
     *
     * @param path
     * @return
     */
    ResponseEntity delete(String path) {
        return delete( path, Object )
    }

    /**
     * Does a DELETE request to the stated path and maps the response JSON to an instance of the type
     * passed. For example, delete('/thing/1', Thing) will result in responseEntity.body being an instance
     * of Thing. (Yes, it's good practice for a REST DELETE to return the thing deleted.)
     *
     * @param path
     * @param type
     * @return
     */
    ResponseEntity delete(String path, Object type) {
        return delete( [path: path, type: type ])
    }

    /**
     * Does a DELETE request using a SimpleRestRequest, initializing its properties with whatever is passed
     * as params.
     *
     * @param params
     * @return
     */
    ResponseEntity delete( Map params ) {
        return delete(new SimpleRestRequest(params))
    }

    /**
     * Does a DELETE request using a SimpleRestRequest.
     *
     * @param params
     * @return
     */
    ResponseEntity delete(SimpleRestRequest simpleRestRequest) {
        return exchange(simpleRestRequest, HttpMethod.DELETE)
    }

    // PROTECTED METHODS

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

}

