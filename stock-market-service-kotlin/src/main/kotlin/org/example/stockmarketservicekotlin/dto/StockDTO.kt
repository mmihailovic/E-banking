package org.example.stockmarketservicekotlin.dto

data class StockDTO(
    val ticker: String,
    val description: String,
    val exchange: String,
    val lastRefresh: Long,
    val price: Double,
    val high: Double,
    val low: Double,
    val change: Double,
    val volume: Long,
    val contractSize: Long,
    val maintenanceMargin: Double,
    val changePercent: Double,
    val dollarVolume: Double,
    val nominalValue: Double,
    val initialMarginCost: Double,
    val outstandingShares: Long,
    val dividendYield: Double,
    val marketCap: Double
)
