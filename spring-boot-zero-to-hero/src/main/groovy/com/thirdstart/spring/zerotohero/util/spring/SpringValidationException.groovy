package com.thirdstart.spring.zerotohero.util.spring

import org.springframework.validation.Errors

/**
 * A simple exception that tacks on a Spring Errors property named 'errors'
 */
class SpringValidationException extends RuntimeException {

    Errors errors
    Object target

    SpringValidationException( String message, Object target, Errors errors ) {
        super( message )
        this.target = target
        this.errors = errors
    }

}
