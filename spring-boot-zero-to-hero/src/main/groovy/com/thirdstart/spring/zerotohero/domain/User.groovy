package com.thirdstart.spring.zerotohero.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Transient
import javax.validation.constraints.NotNull

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @NotNull(message="user.firstname.notnull")
    String username

    /**
     * In a real application, this'd probably be persisted, but we're just tacking on our
     * JWT-passed authorities on the fly in UserService.
     */
    @Transient
    List<Object> authorities

    String toString() {
        return String.format(
                "User[userName='%s']",
                id, username)
    }

    boolean equals(Object obj) {
        return (
            obj instanceof User
            && (
                ( id != null && (obj as User).id == this.id )
                || (id == null && obj.hashCode() == this.hashCode())
            )
        )
    }

}