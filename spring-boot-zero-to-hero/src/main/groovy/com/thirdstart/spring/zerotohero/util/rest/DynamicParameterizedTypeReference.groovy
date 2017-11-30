package com.thirdstart.spring.zerotohero.util.rest

import org.springframework.core.ParameterizedTypeReference
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl

import java.lang.reflect.Type

class DynamicParameterizedTypeReference<T> extends ParameterizedTypeReference<T> {

    Class iterableClass
    Class memberClass

    DynamicParameterizedTypeReference(Class iterableClass, Class memberClass) {
        this.iterableClass = iterableClass
        this.memberClass = memberClass
    }

    @SuppressWarnings("GroovyAssignabilityCheck")
    public Type getType() {
        return new ParameterizedTypeImpl( iterableClass, [ memberClass ].toArray() as Type[], null )
    }
}