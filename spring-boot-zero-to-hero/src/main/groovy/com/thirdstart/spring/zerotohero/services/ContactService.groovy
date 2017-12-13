package com.thirdstart.spring.zerotohero.services

import com.thirdstart.spring.zerotohero.domain.Contact
import com.thirdstart.spring.zerotohero.domain.User
import com.thirdstart.spring.zerotohero.repositories.ContactRepository
import com.thirdstart.spring.zerotohero.util.spring.SimpleValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityNotFoundException


@Transactional
@Component
@PreAuthorize("hasRole('USER')")
class ContactService {

    @Autowired
    ContactRepository contactRepository

    @Autowired
    SimpleValidator simpleValidator

    @Autowired
    UserService userService

    User getCurrentUser() {
        return userService.currentUser
    }

    Contact findOne(Long id) {
        Contact contact = contactRepository.findOne(id)

        if ( !contact ) {
            throw new EntityNotFoundException("No contact found for id ${id}.")
        }

        if ( !contact.canBeAccessedBy( currentUser ) ) {
            throw new AccessDeniedException('Access denied.')
        }

        return contact
    }

    Contact save(Contact contact) {
        // Not intended to be "validation" per se: just an example of an exception raised in the business/server tier
        // that we want to tackle BEFORE validation.
        if ( contact.firstName == 'Nope!' ) {
            throw new Exception("We don't save Nope! around here!")
        }

        if ( contact.id && contact.canBeAccessedBy( currentUser ) ) {
            throw new AccessDeniedException("Access denied.")
        } else if ( !contact.id ) {
            contact.createdBy = userService.currentUser
        }

        simpleValidator.validateAndThrowOnFailure( contact )

        contactRepository.save(contact)

        return contact
    }

    void delete(Contact contact) {

        if ( !contact.canBeAccessedBy( currentUser ) ) {
            throw new AccessDeniedException("Access denied.")
        }

        contactRepository.delete(contact)
    }

    Iterable<Contact> findAll() {
        return contactRepository.findByCreatedBy( currentUser )
    }
}
