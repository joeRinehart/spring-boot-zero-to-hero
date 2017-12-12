package com.thirdstart.spring.zerotohero.repositories

import com.thirdstart.spring.zerotohero.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username)

}
