package org.example.stockmarketservicekotlin.dto

data class StockOverviewDTO(
    val Symbol: String,
    val Description: String,
    val Exchange: String,
    val DividendYield: Double,
    val SharesOutstanding: Long
)
