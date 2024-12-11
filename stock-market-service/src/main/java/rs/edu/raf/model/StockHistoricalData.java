package rs.edu.raf.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("StockHistoricalData")
@Setter
@Getter
public class StockHistoricalData {
    @Id
    private String ticker;
    private List<StockData> historicalData;
}
