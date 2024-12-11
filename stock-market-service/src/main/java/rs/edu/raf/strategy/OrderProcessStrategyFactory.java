package rs.edu.raf.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import rs.edu.raf.model.order.*;


@Component
public class OrderProcessStrategyFactory {
    @Autowired
    @Qualifier("marketOrder")
    private OrderProcessStrategy marketOrderProcessStrategy;

    @Autowired
    @Qualifier("stopOrder")
    private OrderProcessStrategy stopOrderProcessStrategy;

    @Autowired
    @Qualifier("stopLimitOrder")
    private OrderProcessStrategy stopLimitOrderProcessStrategy;

    @Autowired
    @Qualifier("allOrNoneOrder")
    private OrderProcessStrategy allOrNoneOrderProcessStrategy;

    @Autowired
    @Qualifier("limitOrder")
    private OrderProcessStrategy limitOrderProcessStrategy;

    public OrderProcessStrategy getExecutionStrategy(Order order) {
        if(order instanceof MarketOrder) {
            return marketOrderProcessStrategy;
        }

        if(order instanceof StopOrder) {
            return stopOrderProcessStrategy;
        }

        if(order instanceof StopLimitOrder) {
            return stopLimitOrderProcessStrategy;
        }

        if(order instanceof AllOrNoneOrder) {
            return allOrNoneOrderProcessStrategy;
        }

        if(order instanceof LimitOrder) {
            return limitOrderProcessStrategy;
        }

        throw new RuntimeException();
    }
}
