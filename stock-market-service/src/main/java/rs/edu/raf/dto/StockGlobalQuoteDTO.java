package rs.edu.raf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StockGlobalQuoteDTO(@JsonProperty("Global Quote")
                                  GlobalQuoteDTO globalQuote) {
}
