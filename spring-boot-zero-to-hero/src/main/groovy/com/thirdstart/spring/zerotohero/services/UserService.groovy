package com.thirdstart.spring.zerotohero.services

import com.thirdstart.spring.zerotohero.domain.User
import com.thirdstart.spring.zerotohero.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository

    User findUserByUsername(String username) {
        /*
        Something naive and specific to our small test case: if we don't have a given user, we create them.
        */
        List<User> users = userRepository.findByUsername(username)

        User user
        if ( !users.size() ) {
            user = new User(username: username)
            userRepository.save( user )
        } else {
            user = users.first()
        }

        return user
    }

    UserDetails loadUserByUsername(String username) {
        return new org.springframework.security.core.userdetails.User(findUserByUsername(username)?.username, null, [])
    }

    User getCurrentUser() {
        def authentication = SecurityContextHolder.context.authentication
        User user = findUserByUsername( authentication.principal.toString() )

        if ( user ) {
            user.authorities = authentication.authorities
        }

        return user
    }

}
