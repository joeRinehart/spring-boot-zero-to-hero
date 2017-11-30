package com.thirdstart.spring.zerotohero

import com.thirdstart.spring.zerotohero.domain.Contact
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(classes = ZeroToHeroConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactApiSpec extends AbstractRestConfigurationSpec {

    def "We can list all contacts with a GET to /contacts"() {
        setup:
        (0..5).each{
            createARandomContact()
        }

        when:
        ResponseEntity<List<Contact>> response = service.get("/contacts", service.listOf( Contact ))

        then:
        response.body.size() >= 5
        response.body.first() instanceof Contact
    }

    def "We can save a valid contact with a POST to /contacts"() {
        when:
        ResponseEntity<Contact> response = service.post(
            '/contacts',
            new Contact(
                firstName: "Chuck",
                lastName: "Berry"
            )
        )

        then:
        response.statusCode == HttpStatus.OK
        response.body.id > 0
        response.body.firstName == "Chuck"
        response.body.lastName == "Berry"
    }

    def "We can get a contact with a GET to /contacts/:id"() {
        setup:
        Long contactId = createARandomContact().id

        when:
        ResponseEntity<Contact> response = service.get("/contacts/${contactId}", Contact)

        then:
        response.statusCode == HttpStatus.OK
        response.body.id == contactId
    }

    def "We can update a contact with a PUT to /contacts/:id"() {
        setup:
        Contact contact = createARandomContact()
        String newLastName = UUID.randomUUID().toString()

        when:
        contact.lastName = newLastName
        ResponseEntity<Contact> putResponse = service.put("/contacts/${contact.id}", contact)

        then:
        putResponse.statusCode == HttpStatus.OK
        putResponse.body.lastName == newLastName

        when:
        ResponseEntity<Contact> getResponse = service.get("/contacts/${contact.id}", Contact)

        then:
        getResponse.statusCode == HttpStatus.OK
        getResponse.body.id == contact.id
        getResponse.body.lastName == newLastName
    }

    def "We can delete a contact with a DELETE to /contacts/:id"() {
        setup:
        Contact contact = createARandomContact()
        Integer contactCount = service.get('/contacts', service.listOf(Contact) ).body.size()

        expect:
        contactCount > 0

        when:
        service.delete("/contacts/${contact.id}")

        then:
        contactCount == service.get('/contacts', service.listOf(Contact) ).body.size() + 1
    }

    def "Should return 200 when GETing /info from management interface"() {
        when:
        ResponseEntity response = management.get( '/info' )

        then:
        response.statusCode == HttpStatus.OK
    }

    // HELPERS

    private Contact createARandomContact() {
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
