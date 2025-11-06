package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class CreditDTO(
    val creditRequest: CreditRequestDTO,
    val contractDate: Long,
    val loanMaturityDate: Long,
    val installmentAmount: BigDecimal,
    val remainingDebt: BigDecimal,
    val nextInstallmentDate: Long
)
