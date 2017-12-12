package com.thirdstart.spring.zerotohero.util.rest.exceptionhandling

import com.thirdstart.spring.zerotohero.util.spring.LocalMessageDecoder
import com.thirdstart.spring.zerotohero.util.spring.SpringValidationException
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

import javax.persistence.EntityNotFoundException

@Log4j
@ControllerAdvice
class ControllerExceptionAdvice {

    @Autowired
    LocalMessageDecoder localMessageDecoder

    /**
     * Catch-all handler for exceptions. Simply states that something's gone wrong.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> handleException(Exception ex) {
        log.error("Unexpected API Error being returned. Original exception follows.", ex)
        return new ResponseEntity<ApiErrorInformation>(
                new ApiErrorInformation(
                        errorMessage: "API Error",
                        detail: "There's been an error handling your request.",
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    /**
     * Handles Spring Security access denied exceptions
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<ApiErrorInformation>(
                new ApiErrorInformation(
                        errorMessage: "Access denied.",
                        detail: "",
                ),
                HttpStatus.FORBIDDEN
        )
    }

    /**
     * Handler for something not being found: returns a 404/NOT FOUND when an
     * EntityNotFound exception is thrown
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> onEntityNotFound(Exception ex) {
        return new ResponseEntity<ApiErrorInformation>(
                new ApiErrorInformation(
                        errorMessage: "Not found.",
                        detail: ex.message,
                ),
                HttpStatus.NOT_FOUND
        )
    }

    /**
     * Specific handler that takes care of failed @Validated-marked @RequestParam arguments.
     *
     * @param ex
     * @return An ApiErrorInformation where body is the value passed to the argument and messages
     * contain both localized validation messages and their codes, allowing front-end i18n.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> processValidationError(MethodArgumentNotValidException ex) {
        return createValidationFailureEntity(ex.bindingResult.target, ex.bindingResult)
    }

    /**
     * Specific handler that takes care of failed @Validated-marked @RequestParam arguments.
     *
     * @param ex
     * @return An ApiErrorInformation where body is the value passed to the argument and messages
     * contain both localized validation messages and their codes, allowing front-end i18n.
     */
    @ExceptionHandler(SpringValidationException.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> handleSpringValidationException(SpringValidationException ex) {
        return createValidationFailureEntity(ex.target, ex.errors)
    }

    protected ResponseEntity<ApiErrorInformation> createValidationFailureEntity(Object target, Errors errors) {
        return new ResponseEntity<ApiErrorInformation>(
            new ApiErrorInformation(
                errorMessage: "Validation failure",
                detail: "Your request failed validation rules. See 'messages' for further information.",
                body: target,
                messages: errors.fieldErrors.collect { FieldError error ->
                    new ApiErrorMessage( subject: error.field, code: error.codes.first(), message: localMessageDecoder.decode( error.codes as List ) )
                }
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}

