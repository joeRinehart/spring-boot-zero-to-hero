package com.thirdstart.spring.zerotohero.services

import com.thirdstart.spring.zerotohero.domain.Contact
import com.thirdstart.spring.zerotohero.repositories.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class ContactService {

    @Autowired
    ContactRepository contactRepository

    Contact findOne(Long id) {
        return contactRepository.findOne(id)
    }

    Contact save(Contact contact) {

        if ( contact.firstName == 'Nope!' ) {
            throw new Exception("We don't save Nope! around here!")
        }

        return contactRepository.save(contact)
    }

    void delete(Contact contact) {
        contactRepository.delete(contact)
    }

    Iterable<Contact> findAll() {
        return contactRepository.findAll()
    }
}
