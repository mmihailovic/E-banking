package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal

data class ForeignCurrencyBankAccountCreateDTO(
    @NotNull val JMBG: String,
    @NotNull val currencyId: Long,
    @NotNull @PositiveOrZero val maintenancePrice: BigDecimal
)
