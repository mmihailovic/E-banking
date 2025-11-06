package org.example.stockmarketservicekotlin.dto

import jakarta.validation.constraints.NotNull

data class StopOrderCreateDTO(
    @NotNull val orderAction: String, @NotNull val ticker: String, @NotNull val quantity: Int, @NotNull val stop: Double
)
