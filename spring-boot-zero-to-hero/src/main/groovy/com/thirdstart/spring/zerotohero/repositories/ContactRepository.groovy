package com.thirdstart.spring.zerotohero.repositories

import com.thirdstart.spring.zerotohero.domain.Contact
import org.springframework.data.repository.CrudRepository

interface ContactRepository extends CrudRepository<Contact, Long> {

    List<Contact> findByLastName(String lastName)
}
