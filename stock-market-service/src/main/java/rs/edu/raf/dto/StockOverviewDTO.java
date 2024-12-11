package rs.edu.raf.dto;

public record StockOverviewDTO(String Symbol, String Description, String Exchange, Double DividendYield,
                               Long SharesOutstanding) {
}
