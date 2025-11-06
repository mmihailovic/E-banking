package org.example.stockmarketservicekotlin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StockMonthlyDTO(
    @JsonProperty("Meta Data")
    val metaData: Map<String, String>,
    @JsonProperty("Monthly Time Series")
    val timeSeriesMonthly: Map<String, StockHistoryInfoDTO>
)
