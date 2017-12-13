package com.thirdstart.spring.zerotohero.controllers

import com.thirdstart.spring.zerotohero.ApplicationConfiguration
import com.thirdstart.spring.zerotohero.domain.Contact
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Now that we've got security in place, we can make sure users can only manipulate their own contacts.
 */
@SpringBootTest(classes = ApplicationConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactSecuritySpec extends AbstractContactControllerSpec {

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
        withUser('admin_user')
        Long contactId = createARandomContact().id

        when: "we're still the creating user"
        ResponseEntity<Contact> response = service.get("/contacts/${contactId}", Contact)

        then: "we can get our own contact"
        response.statusCode == HttpStatus.OK
        response.body.id == contactId

        when: "we're someone else"
        withUser('test_user')
        ResponseEntity<Map> badResponse = service.get("/contacts/${contactId}", Map)

        then: "we get a 403 trying to get the first user's contact"
        badResponse.statusCode == HttpStatus.FORBIDDEN
    }

    def "We can't save a different user's contacts"() {
        setup: "We create a Contact as admin_user"
        withUser 'admin_user'
        Contact contact = createARandomContact()

        when: "We log in as someone else and try to change the Contact"
        withUser 'test_user'
        contact.firstName = "SomeNewName"
        ResponseEntity response = service.put("/contacts/${contact.id}", contact)

        then: "We get a 403"
        response.statusCode == HttpStatus.FORBIDDEN
    }

    def "We can't delete a different user's contacts"() {
        setup:
        withUser('admin_user')
        Long contactId = createARandomContact().id

        when: "we're still the creating user"
        ResponseEntity<Contact> response = service.get("/contacts/${contactId}", Contact)

        then: "we can get our own contact"
        response.statusCode == HttpStatus.OK
        response.body.id == contactId

        when: "we're someone else and try to delete the user"
        withUser('test_user')
        ResponseEntity<Map> badResponse = service.delete("/contacts/${contactId}", Map)

        then: "we get a 403 trying to delete the first user's contact"
        badResponse.statusCode == HttpStatus.FORBIDDEN
    }

    def "We only see our own contacts when we list"() {
        setup:
        withUser 'admin_user'
        (1..3).each{ createARandomContact() }
        withUser 'test_user'
        (1..5).each{ createARandomContact() }

        when: "we list contacts as admin"
        withUser 'admin_user'
        ResponseEntity<List<Contact>> response = service.get("/contacts", service.listOf(Contact))

        then: "We only receive contacts that belong to us"
        response.body.find{ it.createdBy.username != 'admin_user' } == null
    }

}
