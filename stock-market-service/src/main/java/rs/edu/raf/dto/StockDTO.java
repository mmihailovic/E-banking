package rs.edu.raf.dto;

public record StockDTO (String ticker,
                        String description,
                        String exchange,
                        Long lastRefresh,
                        Double price,
                        Double high,
                        Double low,
                        Double change,
                        Long volume,
                        Long contractSize,
                        Double maintenanceMargin,
                        Double changePercent,
                        Double dollarVolume,
                        Double nominalValue,
                        Double initialMarginCost,
                        Long outstandingShares,
                        Double dividendYield,
                        Double marketCap) {
}
