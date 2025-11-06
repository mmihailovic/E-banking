package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class CreditRequestCreateDTO(
    @NotNull val creditTypeId: Long,
    @NotNull @Positive val amount: BigDecimal,
    @NotNull val loanPurpose: String,
    @NotNull @PositiveOrZero val salary: BigDecimal,
    @NotNull val phoneNumber: String,
    val permanentEmployee: Boolean,
    val currentEmploymentPeriod: Int,
    val loanTerm: Long,
    @NotNull val bankAccountNumber: Long
)
