package rs.edu.raf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record StockWeeklyDTO(
    @JsonProperty("Meta Data")
    Map<String, String> metaData,
    @JsonProperty("Weekly Time Series")
    Map<String, StockHistoryInfoDTO> timeSeriesWeekly
) { }
