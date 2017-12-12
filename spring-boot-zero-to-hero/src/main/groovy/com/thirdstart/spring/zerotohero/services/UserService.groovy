package com.thirdstart.spring.zerotohero.services

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService implements UserDetailsService {

    UserDetails loadUserByUsername(String username) {
        return new User(username: username)
    }

    User getCurrentUser() {
        def authentication = SecurityContextHolder.context.authentication

        return new User(authentication.principal, authentication.principal, authentication.authorities ?: [])
    }

}
