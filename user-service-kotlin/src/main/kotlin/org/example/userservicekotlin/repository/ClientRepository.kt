package org.example.userservicekotlin.repository

import org.example.userservicekotlin.model.user.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
    fun findByEmailAndActiveIsTrue(email: String): Optional<Client>

    fun findByPhoneNumberAndActiveIsTrue(phoneNumber: String): Optional<Client>

    fun findByJMBGAndActiveIsTrue(jmbg: String): Optional<Client>

    fun findAllByActiveIsTrue(): List<Client>

    fun findByIdAndActiveIsTrue(id: Long): Optional<Client>
}