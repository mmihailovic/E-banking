package org.example.stockmarketservicekotlin.model

import org.springframework.data.redis.core.RedisHash

@RedisHash("stock")
class Stock(
    ticker: String,
    description: String,
    exchange: String,
    lastRefresh: Long,
    price: Double,
    high: Double,
    low: Double,
    change: Double,
    volume: Long,
    contractSize: Long,
    maintenanceMargin: Double,
    changePercent: Double,
    dollarVolume: Double,
    nominalValue: Double,
    initialMarginCost: Double,
    val outstandingShares: Long,
    val dividendYield: Double,
    val marketCap: Double
) : Listing(
    ticker,
    description,
    exchange,
    lastRefresh,
    price,
    high,
    low,
    change,
    volume,
    contractSize,
    maintenanceMargin,
    changePercent,
    dollarVolume,
    nominalValue,
    initialMarginCost
) {
}