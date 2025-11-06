package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.ExchangeAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExchangeAccountRepository : JpaRepository<ExchangeAccount, Long> {
    fun findByCurrencyCode(currency: String): Optional<ExchangeAccount>
}