package com.thirdstart.spring.zerotohero.domain

import javax.persistence.*
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
                "User[id='%s', userName='%s']",
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