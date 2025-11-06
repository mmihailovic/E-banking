package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CurrencyCreateDTO(
    @NotNull @NotEmpty val name: String,
    @NotNull @NotEmpty val code: String,
    @NotNull @NotEmpty val symbol: String,
    @NotNull val countryId: Long
)
