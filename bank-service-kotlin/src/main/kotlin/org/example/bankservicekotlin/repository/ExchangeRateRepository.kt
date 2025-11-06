package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.ExchangeRate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExchangeRateRepository:JpaRepository<ExchangeRate, Long> {
    fun findByCurrencyCode(currencyCode: String): Optional<ExchangeRate>
}