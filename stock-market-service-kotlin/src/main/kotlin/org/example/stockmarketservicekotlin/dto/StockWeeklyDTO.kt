package org.example.stockmarketservicekotlin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StockWeeklyDTO(
    @JsonProperty("Meta Data")
    val metaData: Map<String, String>,
    @JsonProperty("Weekly Time Series")
    val timeSeriesWeekly: Map<String, StockHistoryInfoDTO>
)
