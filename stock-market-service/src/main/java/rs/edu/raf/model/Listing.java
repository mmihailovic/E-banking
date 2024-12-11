package rs.edu.raf.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public abstract class Listing {
    @Id
    private String ticker;
    private String description;
    private String exchange;
    private Long lastRefresh;
    private Double price;
    private Double high;
    private Double low;
    private Double change;
    private Long volume;
    private Long contractSize;
    private Double maintenanceMargin;
    private Double changePercent;
    private Double dollarVolume;
    private Double nominalValue;
    private Double initialMarginCost;

    public Listing(String ticker, String description, String exchange, Long lastRefresh, Double price, Double high,
                   Double low, Double change, Long volume, Long contractSize, Double maintenanceMargin) {
        this.ticker = ticker;
        this.description = description;
        this.exchange = exchange;
        this.lastRefresh = lastRefresh;
        this.price = price;
        this.high = high;
        this.low = low;
        this.change = change;
        this.volume = volume;
        this.contractSize = contractSize;
        this.maintenanceMargin = maintenanceMargin;
        this.changePercent = (100 * change) / (price - change);
        this.dollarVolume = volume * price;
        this.nominalValue = contractSize * price;
        this.initialMarginCost = maintenanceMargin * 1.1;
    }
}
