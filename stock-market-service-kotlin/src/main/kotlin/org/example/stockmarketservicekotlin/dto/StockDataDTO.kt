package org.example.stockmarketservicekotlin.dto

data class StockDataDTO(
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long
)
