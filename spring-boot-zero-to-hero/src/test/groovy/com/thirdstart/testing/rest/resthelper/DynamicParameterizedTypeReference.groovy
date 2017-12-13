package com.thirdstart.testing.rest.resthelper

import org.springframework.core.ParameterizedTypeReference
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl

import java.lang.reflect.Type

class DynamicParameterizedTypeReference<T> extends ParameterizedTypeReference<T> {

    Class parameterizableClass
    Class memberClass

    DynamicParameterizedTypeReference(Class parameterizableClass, Class memberClass) {
        this.parameterizableClass = parameterizableClass
        this.memberClass = memberClass
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    Type getType() {
        return ParameterizedTypeImpl.make( parameterizableClass, [ memberClass ].toArray() as Type[], null )
    }
}