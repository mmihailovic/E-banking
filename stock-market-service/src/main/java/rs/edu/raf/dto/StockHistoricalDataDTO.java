package rs.edu.raf.dto;

import java.util.List;

public record StockHistoricalDataDTO(String ticker, List<StockDataDTO> historicalData) {
}
