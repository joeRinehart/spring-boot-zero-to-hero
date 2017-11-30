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

    @Autowired
    ContactService contactService

    @GetMapping('/contacts')
    ResponseEntity<Iterable> list() {
        return new ResponseEntity<Iterable>(contactService.findAll(), HttpStatus.OK)
    }

    @GetMapping('/contacts/{id}')
    ResponseEntity<Contact> get( @PathVariable('id') Long id ) {
        Contact contact = contactService.findOne( id )
        return new ResponseEntity(contact, HttpStatus.OK)
    }

    @PostMapping('/contacts')
    ResponseEntity<Contact> post( @RequestBody Contact contactDto ) {
        Contact contact = new Contact()

        contact.firstName = contactDto.firstName
        contact.lastName = contactDto.lastName

        contactService.save( contact )

        return new ResponseEntity(contact, HttpStatus.OK)
    }

    @PutMapping('/contacts/{id}')
    ResponseEntity<Contact> put( @RequestBody Contact contactDto ) {
        Contact contact = contactService.findOne( contactDto.id )

        contact.firstName = contactDto.firstName
        contact.lastName = contactDto.lastName

        contactService.save( contact )

        return new ResponseEntity(contact, HttpStatus.OK)
    }

    @DeleteMapping('/contacts/{id}')
    ResponseEntity<Contact> delete( @PathVariable('id') Long id ) {
        Contact contact = contactService.findOne( id )

        contactService.delete( contact )

        return new ResponseEntity(contact, HttpStatus.OK)
    }


}
