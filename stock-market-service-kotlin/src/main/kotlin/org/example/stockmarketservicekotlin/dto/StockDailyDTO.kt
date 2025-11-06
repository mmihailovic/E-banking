package org.example.stockmarketservicekotlin.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StockDailyDTO(@JsonProperty("Meta Data")
                         val metaData:Map<String, String>,
                         @JsonProperty("Time Series (Daily)")
                         val timeSeriesDaily: Map<String, StockHistoryInfoDTO>)
