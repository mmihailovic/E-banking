package rs.edu.raf.strategy;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.edu.raf.exceptions.StockDoesntExist;
import rs.edu.raf.model.*;
import rs.edu.raf.model.order.LimitOrder;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.model.order.StopLimitOrder;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;
import rs.edu.raf.repository.StockRepository;

@Component("stopLimitOrder")
public class StopLimitOrderStrategy extends AbstractOrderProcessStrategy {
    private StockRepository stockRepository;
    private OrderRepository orderRepository;

    public StopLimitOrderStrategy(@Value("${update.balance.exchange}") String UPDATE_BALANCE_EXCHANGE,
                                      @Value("${update.balance.routing.key}") String UPDATE_BALANCE_ROUTING_KEY, OrderRepository orderRepository, ListingOwnerRepository listingOwnerRepository, RabbitTemplate rabbitTemplate, Gson gson, StockRepository stockRepository) {
        super(UPDATE_BALANCE_EXCHANGE, UPDATE_BALANCE_ROUTING_KEY, orderRepository, listingOwnerRepository, rabbitTemplate, gson);
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void processOrder(Order order) {
        if(!isOrderExecutable(order)) {
            return;
        }

        StopLimitOrder stopLimitOrder = (StopLimitOrder) order;
        orderRepository.save(createLimitOrder(stopLimitOrder));
        orderRepository.delete(stopLimitOrder);
    }

    @Override
    public boolean isOrderExecutable(Order order) {
        String ticker = order.getTicker();

        Stock stock = stockRepository.findById(ticker).orElseThrow(()->new StockDoesntExist(ticker));

        StopLimitOrder stopLimitOrder = (StopLimitOrder) order;

        return (stock.getLow() < stopLimitOrder.getStop() && stopLimitOrder.getOrderAction() == OrderAction.SELL) ||
                (stock.getHigh() > stopLimitOrder.getStop() && stopLimitOrder.getOrderAction() == OrderAction.BUY);
    }

    private LimitOrder createLimitOrder(StopLimitOrder stopLimitOrder) {
        LimitOrder limitOrder = new LimitOrder();

        limitOrder.setLimitAmount(stopLimitOrder.getLimitAmount());
        limitOrder.setOrderAction(stopLimitOrder.getOrderAction());
        limitOrder.setQuantity(stopLimitOrder.getQuantity());
        limitOrder.setTicker(stopLimitOrder.getTicker());
        limitOrder.setOrderCreator(stopLimitOrder.getOrderCreator());

        return limitOrder;
    }
}
