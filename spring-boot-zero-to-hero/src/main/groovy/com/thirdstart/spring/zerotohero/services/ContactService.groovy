package com.thirdstart.spring.zerotohero.services

import com.thirdstart.spring.zerotohero.domain.Contact
import com.thirdstart.spring.zerotohero.repositories.ContactRepository
import com.thirdstart.spring.zerotohero.util.spring.SimpleValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException


@Transactional
@Component
class ContactService {

    @Autowired
    ContactRepository contactRepository

    @Autowired
    SimpleValidator simpleValidator


    Contact findOne(Long id) {
        Contact contact = contactRepository.findOne(id)

        if ( !contact ) {
            throw new EntityNotFoundException("No contact found for id ${id}.")
        }

        return contact
    }

    Contact save(Contact contact) {

        // Not intended to be "validation" per se: just an example of an exception raised in the business/server tier
        // that we want to tackle BEFORE validation.
        if ( contact.firstName == 'Nope!' ) {
            throw new Exception("We don't save Nope! around here!")
        }

        simpleValidator.validateAndThrowOnFailure( contact )

        contactRepository.save(contact)

        return contact
    }

    void delete(Contact contact) {
        contactRepository.delete(contact)
    }

    Iterable<Contact> findAll() {
        return contactRepository.findAll()
    }
}
