package org.example.stockmarketservicekotlin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StockHistoryInfoDTO(
    @JsonProperty("1. open")
    val open: Double,
    @JsonProperty("2. high")
    val high: Double,
    @JsonProperty("3. low")
    val low: Double,
    @JsonProperty("4. close")
    val close: Double,
    @JsonProperty("5. volume")
    val volume: Long
)
