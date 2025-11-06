package org.example.stockmarketservicekotlin.dto

import jakarta.validation.constraints.NotNull

data class LimitOrderCreateDTO(
    @NotNull val orderAction: String,
    @NotNull val ticker: String,
    @NotNull val quantity: Int,
    @NotNull val limit: Double
)
