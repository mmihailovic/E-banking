package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class CurrentBankAccountCreateDTO(
    @NotNull val JMBG: String,
    @NotNull val accountType: String,
    @PositiveOrZero val maintenancePrice: BigDecimal
)
