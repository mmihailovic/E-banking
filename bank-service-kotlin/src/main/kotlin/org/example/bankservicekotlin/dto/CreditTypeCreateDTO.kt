package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class CreditTypeCreateDTO(
    @NotNull val name: String,
    @NotNull @Positive val nominalInterestRate: BigDecimal,
    @PositiveOrZero val currencyId: Long,
    val minLoanTerm: Int,
    val maxLoanTerm: Int,
    @NotNull @Positive val maxLoanAmount: BigDecimal,
    val prepayment: BigDecimal
)
