package rs.edu.raf.repository;

import org.springframework.data.repository.CrudRepository;
import rs.edu.raf.model.StockHistoricalData;

public interface StockHistoricalDataRepository extends CrudRepository<StockHistoricalData, String> {
}
