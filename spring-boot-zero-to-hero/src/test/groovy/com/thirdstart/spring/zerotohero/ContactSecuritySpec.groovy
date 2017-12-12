package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.domain.Contact
import com.thirdstart.spring.zerotohero.util.rest.exceptionhandling.ApiErrorInformation
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Now that we've got security in place, we can make sure users can only manipulate their own contacts.
 */
@SpringBootTest(classes = ZeroToHeroConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactSecuritySpec extends AbstractContactApiSpec {

    def "An unauthenticated user can't create a contact"() {
        when:
        ResponseEntity<Map> response = service.post(
                '/contacts',
                new Contact(
                        firstName: "Random",
                        lastName: UUID.randomUUID().toString()
                ),
                Map
        )

        then:
        response.statusCode == HttpStatus.FORBIDDEN
    }

    def "We can't get a different user's contacts"() {
        setup:
        authenticateAs('admin_user')
        Long contactId = createARandomContact().id

        when: "we're still the creating user"
        ResponseEntity<Contact> response = service.get("/contacts/${contactId}", Contact)

        then: "we can get our own contact"
        response.statusCode == HttpStatus.OK
        response.body.id == contactId

        when: "we're someone else"
        authenticateAs('test_user')
        ResponseEntity<Map> badResponse = service.get("/contacts/${contactId}", Map)

        then: "we get a 403 trying to get the first user's contact"
        badResponse.statusCode == HttpStatus.FORBIDDEN
    }

    def "We can't save a different user's contacts"() {
        setup:
        authenticateAs('admin_user')
        Long contactId = createARandomContact().id

        when: "we're still the creating user"
        ResponseEntity<Contact> response = service.get("/contacts/${contactId}", Contact)

        then: "we can get our own contact"
        response.statusCode == HttpStatus.OK
        response.body.id == contactId

        when: "we're someone else and try to change the user"
        authenticateAs('test_user')
        response.body.firstName = "SomeNewName"
        ResponseEntity<Map> badResponse = service.put("/contacts/${contactId}", response.body, Map)

        then: "we get a 403 trying to save the first user's contact"
        badResponse.statusCode == HttpStatus.FORBIDDEN
    }


    def "We can't delete a different user's contacts"() {
        setup:
        authenticateAs('admin_user')
        Long contactId = createARandomContact().id

        when: "we're still the creating user"
        ResponseEntity<Contact> response = service.get("/contacts/${contactId}", Contact)

        then: "we can get our own contact"
        response.statusCode == HttpStatus.OK
        response.body.id == contactId

        when: "we're someone else and try to delete the user"
        authenticateAs('test_user')
        ResponseEntity<Map> badResponse = service.delete("/contacts/${contactId}", Map)

        then: "we get a 403 trying to delete the first user's contact"
        badResponse.statusCode == HttpStatus.FORBIDDEN
    }

}
