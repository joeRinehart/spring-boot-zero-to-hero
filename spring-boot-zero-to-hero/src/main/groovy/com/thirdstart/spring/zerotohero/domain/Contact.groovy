package com.thirdstart.spring.zerotohero.domain

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.validation.constraints.NotNull;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @NotNull(message="contact.firstname.notnull")
    String firstName

    @NotNull(message="contact.lastname.notnull")
    String lastName

    String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName)
    }

}