package rs.edu.raf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record StockMonthlyDTO(
    @JsonProperty("Meta Data")
    Map<String, String> metaData,
    @JsonProperty("Monthly Time Series")
    Map<String, StockHistoryInfoDTO> timeSeriesMonthly
) { }
