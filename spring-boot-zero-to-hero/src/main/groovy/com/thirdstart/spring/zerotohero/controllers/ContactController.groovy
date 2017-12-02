package com.thirdstart.spring.zerotohero.controllers

import com.thirdstart.spring.zerotohero.domain.Contact
import com.thirdstart.spring.zerotohero.services.ContactService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class ContactController {

    // PROPERTIES

    @Autowired
    ContactService contactService


    // REST API METHODS

    @GetMapping('/contacts')
    ResponseEntity<Iterable> list() {
        return new ResponseEntity<Iterable>(contactService.findAll(), HttpStatus.OK)
    }

    @GetMapping('/contacts/{id}')
    ResponseEntity<Contact> get( @PathVariable('id') Long id ) {
        Contact contact = contactService.findOne( id )

        return ok(contact)
    }

    @PostMapping('/contacts')
    ResponseEntity<Contact> post( @RequestBody Contact contactDto ) {
        Contact contact = assemble( contactDto )

        contactService.save( contact )

        return ok(contact)
    }

    @PutMapping('/contacts/{id}')
    ResponseEntity<Contact> put( @RequestBody Contact contactDto ) {
        Contact contact = contactService.findOne( contactDto.id )

        assemble( contactDto, contact )

        contactService.save( contact )

        return ok(contact)
    }

    @DeleteMapping('/contacts/{id}')
    ResponseEntity<Contact> delete( @PathVariable('id') Long id ) {
        Contact contact = contactService.findOne( id )

        contactService.delete( contact )

        return ok(contact)
    }

    // PROTECTED METHODS

    /**
     * Marshall/Assemble an incoming Contact (DTO) onto a Contact. In a larger API,
     * this could definitely be passed off to something dedicated to marshalling!
     */
    Contact assemble(Contact dto, Contact contact = new Contact()) {
        contact.firstName = dto.firstName
        contact.lastName = dto.lastName
        return contact

    }

    /**
     * Create an HttpStatus.OK (200) response containing a contact
     */
    ResponseEntity ok(Contact contact) {
        return new ResponseEntity( contact, HttpStatus.OK )
    }
}
