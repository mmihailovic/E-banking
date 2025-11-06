package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class CreditTypeDTO(
    val id: Long?,
    val name: String,
    val nominalInterestRate: BigDecimal,
    val minLoanTerm: Int,
    val maxLoanTerm: Int,
    val maxLoanAmount: BigDecimal,
    val prepayment: BigDecimal,
    val currencyDTO: CurrencyDTO
)
