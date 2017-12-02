package com.thirdstart.spring.zerotohero.util.rest.exceptionhandling

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

@ControllerAdvice
class ControllerMethodArgumentValidationHandler {

    @Autowired
    MessageSource messageSource

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity<ApiErrorInformation> processValidationError(MethodArgumentNotValidException ex) {
        List<ApiErrorMessage> errors = []

        ex.bindingResult.fieldErrors.each { FieldError error ->
            ApiErrorMessage apiErrorMessage = new ApiErrorMessage( code: error.getDefaultMessage() )
            // TODO: there's got to be a better, non-try/catch way to do this
            try {
                apiErrorMessage.message = messageSource.getMessage(error.getDefaultMessage(), null, LocaleContextHolder.locale)
            } catch ( NoSuchMessageException noSuchMessageException ) {
                apiErrorMessage.message = apiErrorMessage.code
            }
            errors << apiErrorMessage
        }

        return new ResponseEntity<ApiErrorInformation>(
            new ApiErrorInformation(
                    errorMessage: "Validation failure",
                    detail: "Your request failed validation rules. See 'messages' for further information.",
                    body: ex.bindingResult.target,
                    messages: errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}

