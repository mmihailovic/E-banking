package org.example.bankservicekotlin.dto

import jakarta.validation.constraints.NotNull

data class CardIssuerCreateDTO(@NotNull val name: String, val MII: Int, val BIN: Int)
