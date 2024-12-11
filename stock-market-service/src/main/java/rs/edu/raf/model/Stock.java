package rs.edu.raf.model;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Stock")
@Getter
public class Stock extends Listing {
    private Long outstandingShares;
    private Double dividendYield;
    private Double marketCap;

    public Stock(String ticker, String description, String exchange, Long lastRefresh, Double price, Double high,
                 Double low, Double change, Long volume, Long outstandingShares, Double dividendYield) {
        super(ticker, description, exchange, lastRefresh, price, high, low, change, volume, 1L, price / 2);
        this.outstandingShares = outstandingShares;
        this.dividendYield = dividendYield;
        this.marketCap = outstandingShares * price;
    }
}
