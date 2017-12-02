package com.thirdstart.spring.zerotohero.util.rest.exceptionhandling

import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@Log4j
@ControllerAdvice
class ControllerExceptionAdvice {

    @Autowired
    MessageSource messageSource

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
     * Specific handler that takes care of failed @Validated-marked @RequestParam arguments.
     *
     * @param ex
     * @return An ApiErrorInformation where body is the value passed to the argument and messages
     * contain both localized validation messages and their codes, allowing front-end i18n.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> processValidationError(MethodArgumentNotValidException ex) {
        return new ResponseEntity<ApiErrorInformation>(
            new ApiErrorInformation(
                    errorMessage: "Validation failure",
                    detail: "Your request failed validation rules. See 'messages' for further information.",
                    body: ex.bindingResult.target,
                    messages: ex.bindingResult.fieldErrors.collect { FieldError error ->
                        createApiErrorMessageForFieldError( error)
                    }
            ),
            HttpStatus.BAD_REQUEST
        )
    }


    protected createApiErrorMessageForFieldError( FieldError error ) {
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage( subject: error.field, code: error.codes.first() )

        error.codes.each{ String code ->
            // TODO: there's got to be a better, non-try/catch way to do this
            try {
                apiErrorMessage.message = messageSource.getMessage(code, null, LocaleContextHolder.locale)
            } catch ( NoSuchMessageException noSuchMessageException ) {
                apiErrorMessage.message = apiErrorMessage.message ?: apiErrorMessage.code
            }
        }

        return apiErrorMessage
    }
}

