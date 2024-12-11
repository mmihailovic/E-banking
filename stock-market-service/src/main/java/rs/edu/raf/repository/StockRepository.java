package rs.edu.raf.repository;

import org.springframework.data.repository.CrudRepository;
import rs.edu.raf.model.Stock;

public interface StockRepository extends CrudRepository<Stock, String> {
}
