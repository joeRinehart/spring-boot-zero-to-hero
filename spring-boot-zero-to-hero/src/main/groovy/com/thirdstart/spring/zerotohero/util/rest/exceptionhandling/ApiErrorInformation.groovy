package com.thirdstart.spring.zerotohero.util.rest.exceptionhandling

class ApiErrorInformation<T> {

    String detail
    String errorMessage
    T body
    List<ApiErrorMessage> messages

}
