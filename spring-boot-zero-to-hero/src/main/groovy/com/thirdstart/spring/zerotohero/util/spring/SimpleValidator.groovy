package com.thirdstart.spring.zerotohero.util.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Component
/**
 * Provides a simple wrapper for the mechanics of manually doing a Spring validation within a
 * Spring application that provides a LocalValidatorFactoryBean (like Spring Boot's web starter)
 */
class SimpleValidator {

    @Autowired
    LocalValidatorFactoryBean validator

    Errors validateAndThrowOnFailure(Object target) {
        return validate( target,true )
    }

    Errors validate(Object target, Boolean throwOnErrors = false) {
        return validate( target, target.class.name.tokenize('.').last().toLowerCase(), throwOnErrors )
    }

    Errors validate(Object target, String targetName, Boolean throwOnErrors = false) {
        Errors errors = new BeanPropertyBindingResult(target, targetName)
        validator.validate(target, errors)

        if ( errors.hasErrors() && throwOnErrors ) {
            throwExceptionFor( target, errors )
        }
        return errors
    }

    void throwExceptionFor(Object target, Errors errors) {
        throw new SpringValidationException( "Validation failed", target, errors )
    }

}
