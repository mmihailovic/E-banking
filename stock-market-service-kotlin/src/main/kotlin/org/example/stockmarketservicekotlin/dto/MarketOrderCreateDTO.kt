package org.example.stockmarketservicekotlin.dto

import jakarta.validation.constraints.NotNull

data class MarketOrderCreateDTO(
    @NotNull val orderAction: String,
    @NotNull val ticker: String,
    @NotNull val quantity: Int
)
