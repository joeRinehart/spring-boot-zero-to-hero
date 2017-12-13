package com.thirdstart.spring.zerotohero.controllers

import com.thirdstart.spring.zerotohero.domain.Contact
import com.thirdstart.testing.rest.abstractspecs.AbstractSecuredRestControllerSpec
import org.springframework.http.ResponseEntity

abstract class AbstractContactControllerSpec extends AbstractSecuredRestControllerSpec {

    protected Contact createARandomContact() {
        ResponseEntity<Contact> response = service.post(
                '/contacts',
                new Contact(
                        firstName: "Random",
                        lastName: UUID.randomUUID().toString()
                )
        )

        return response.body
    }
}
