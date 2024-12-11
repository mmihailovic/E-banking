package rs.edu.raf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
public record StockHistoryInfoDTO(
    @JsonProperty("1. open")
    Double open,
    @JsonProperty("2. high")
    Double high,
    @JsonProperty("3. low")
    Double low,
    @JsonProperty("4. close")
    Double close,
    @JsonProperty("5. volume")
    Long volume
) { }
