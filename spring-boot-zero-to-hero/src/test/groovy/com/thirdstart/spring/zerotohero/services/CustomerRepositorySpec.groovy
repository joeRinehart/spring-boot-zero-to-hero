package com.thirdstart.spring.zerotohero.services

import com.thirdstart.spring.zerotohero.domain.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class CustomerRepositorySpec extends Specification {

    @Autowired
    CustomerRepository customerRepository

    def "We can list customers by their last name"() {
        setup:
        String lastName = UUID.randomUUID().toString()
        Integer startingCount = customerRepository.findByLastName( lastName ).size()

        when:
        customerRepository.save(
            new Customer(
                firstName: "Chuck",
                lastName: lastName,
            )
        )

        then:
        startingCount + 1 == customerRepository.findByLastName( lastName ).size()
    }
}
