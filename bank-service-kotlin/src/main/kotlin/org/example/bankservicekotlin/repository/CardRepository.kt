package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.card.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CardRepository:JpaRepository<Card,Long> {
    fun findByNumber(number: String): Optional<Card>
    fun findByBankAccount_Owner(owner: Long): List<Card>
}