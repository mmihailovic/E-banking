package org.example.stockmarketservicekotlin.dto

data class StockHistoricalDataDTO(val ticker: String, val historicalData: List<StockDataDTO>)
