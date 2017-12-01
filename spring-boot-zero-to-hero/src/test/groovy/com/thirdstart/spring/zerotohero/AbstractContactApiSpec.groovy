package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.domain.Contact
import org.springframework.http.ResponseEntity

abstract class AbstractContactApiSpec extends AbstractRestConfigurationSpec {

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
