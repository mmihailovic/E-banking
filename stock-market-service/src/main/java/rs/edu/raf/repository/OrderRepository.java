package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByTickerAndOrderActionAndQuantityGreaterThan(String ticker, OrderAction orderAction, int quantity);
    List<Order> findAllByQuantityGreaterThan(int quantity);
}
