package rs.edu.raf.strategy;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.edu.raf.exceptions.StockDoesntExist;
import rs.edu.raf.model.Stock;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;
import rs.edu.raf.repository.StockRepository;

@Component("marketOrder")
public class MarketOrderProcessStrategy extends AbstractOrderProcessStrategy{
    private StockRepository stockRepository;

    public MarketOrderProcessStrategy(@Value("${update.balance.exchange}") String UPDATE_BALANCE_EXCHANGE,
                                      @Value("${update.balance.routing.key}") String UPDATE_BALANCE_ROUTING_KEY, OrderRepository orderRepository, ListingOwnerRepository listingOwnerRepository, RabbitTemplate rabbitTemplate, Gson gson, StockRepository stockRepository) {
        super(UPDATE_BALANCE_EXCHANGE, UPDATE_BALANCE_ROUTING_KEY, orderRepository, listingOwnerRepository, rabbitTemplate, gson);
        this.stockRepository = stockRepository;
    }

    @Override
    public void processOrder(Order order) {
        if(!isOrderExecutable(order)) {
            return;
        }
        String ticker = order.getTicker();
        Stock stock = stockRepository.findById(ticker).orElseThrow(()->new StockDoesntExist(ticker));

        if(order.getOrderAction() == OrderAction.BUY) {
            processBuyOrder(order, stock.getHigh(), stock.getLow());
        }
        else {
            processSellOrder(order, stock.getHigh(), stock.getLow());
        }
    }

    @Override
    public boolean isOrderExecutable(Order order) {
        return true;
    }
}
