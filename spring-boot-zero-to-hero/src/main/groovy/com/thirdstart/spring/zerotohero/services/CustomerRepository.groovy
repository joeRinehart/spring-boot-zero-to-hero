package com.thirdstart.spring.zerotohero.services

import com.thirdstart.spring.zerotohero.domain.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName)
}
