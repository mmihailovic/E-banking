package org.example.stockmarketservicekotlin.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("StockHistoricalData")
class StockHistoricalData(
    @Id
    val ticker: String,
    val historicalData: List<StockData>) {
}