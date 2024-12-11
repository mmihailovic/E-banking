package rs.edu.raf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record StockDailyDTO(
    @JsonProperty("Meta Data")
    Map<String, String> metaData,
    @JsonProperty("Time Series (Daily)")
    Map<String, StockHistoryInfoDTO> timeSeriesDaily
) {}
