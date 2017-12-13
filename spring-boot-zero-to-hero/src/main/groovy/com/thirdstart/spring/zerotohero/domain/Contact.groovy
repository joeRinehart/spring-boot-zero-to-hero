package com.thirdstart.spring.zerotohero.domain

import javax.persistence.Entity
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
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

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="created_by_id")
    @NotNull(message="contact.createdBy.notnull")
    User createdBy

    Boolean canBeAccessedBy(User user) {
        return user == createdBy
    }

    String toString() {
        return String.format(
            "Contact[id=%d, firstName='%s', lastName='%s', createdBy='%s']",
            id, firstName, lastName, createdBy?.username
        )
    }

}