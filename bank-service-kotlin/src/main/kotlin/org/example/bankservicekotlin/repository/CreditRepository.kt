package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.credit.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface CreditRepository: JpaRepository<Credit, Long> {
    fun findAllByRemainingDebtGreaterThan(balance: BigDecimal): List<Credit>
}