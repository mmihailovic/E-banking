package org.example.stockmarketservicekotlin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GlobalQuoteDTO(
    @JsonProperty("03. high")
    val high: Double,
    @JsonProperty("04. low")
    val low: Double,
    @JsonProperty("05. price")
    val price: Double,
    @JsonProperty("06. volume")
    val volume: Long,
    @JsonProperty("09. change")
    val change: Double
)
