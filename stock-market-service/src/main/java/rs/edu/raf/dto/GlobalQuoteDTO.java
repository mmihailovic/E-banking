package rs.edu.raf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GlobalQuoteDTO(
        @JsonProperty("03. high")
        Double high,
        @JsonProperty("04. low")
        Double low,
        @JsonProperty("05. price")
        Double price,
        @JsonProperty("06. volume")
        Long volume,
        @JsonProperty("09. change")
        Double change
) {
}
