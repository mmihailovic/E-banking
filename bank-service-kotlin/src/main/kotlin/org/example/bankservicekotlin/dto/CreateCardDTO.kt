package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class CreateCardDTO(
    @NotNull val type: String,
    @NotNull val issuerId: Long,
    @NotNull val name: String,
    @NotNull val bankAccountNumber: Long,
    @NotNull @Positive val limit: BigDecimal
)
