package rs.edu.raf.strategy;

import rs.edu.raf.model.order.Order;

public interface OrderProcessStrategy {
    /**
     * Process single order
     * @param order the order to process
     */
    void processOrder(Order order);
}
