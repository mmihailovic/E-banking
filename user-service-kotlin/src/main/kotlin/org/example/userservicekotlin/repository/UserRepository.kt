package org.example.userservicekotlin.repository

import org.example.userservicekotlin.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmailAndActiveIsTrue(email: String): Optional<User>
}