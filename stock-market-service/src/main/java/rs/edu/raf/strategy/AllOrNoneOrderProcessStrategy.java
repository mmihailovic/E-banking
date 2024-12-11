package rs.edu.raf.strategy;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.edu.raf.exceptions.StockDoesntExist;
import rs.edu.raf.model.ListingOwner;
import rs.edu.raf.model.Stock;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;
import rs.edu.raf.repository.StockRepository;

import java.util.List;

@Component("allOrNoneOrder")
public class AllOrNoneOrderProcessStrategy extends AbstractOrderProcessStrategy {
    private OrderRepository orderRepository;
    private StockRepository stockRepository;
    private ListingOwnerRepository listingOwnerRepository;

    public AllOrNoneOrderProcessStrategy(@Value("${update.balance.exchange}") String UPDATE_BALANCE_EXCHANGE,
                                      @Value("${update.balance.routing.key}") String UPDATE_BALANCE_ROUTING_KEY, OrderRepository orderRepository, ListingOwnerRepository listingOwnerRepository, RabbitTemplate rabbitTemplate, Gson gson, StockRepository stockRepository) {
        super(UPDATE_BALANCE_EXCHANGE, UPDATE_BALANCE_ROUTING_KEY, orderRepository, listingOwnerRepository, rabbitTemplate, gson);
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.listingOwnerRepository = listingOwnerRepository;
    }

    @Override
    public void processOrder(Order order) {
        if(!isOrderExecutable(order)) {
            return;
        }
        String ticker = order.getTicker();

        Stock stock = stockRepository.findById(ticker).orElseThrow(()->new StockDoesntExist(ticker));

        if(order.getOrderAction() == OrderAction.BUY) {
            checkSellOrders(order, stock.getHigh(), stock.getLow());
        }

        else {
            checkBuyOrders(order, stock.getHigh(), stock.getLow());
        }
    }

    @Override
    public boolean isOrderExecutable(Order order) {
        return true;
    }

    private void checkSellOrders(Order order, Double buyPrice, Double sellPrice) {
        List<Order> sellOrders = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
                order.getTicker(), OrderAction.SELL, 0);

        for(Order sellOrder: sellOrders) {
            if(order.getOrderCreator().longValue() == sellOrder.getOrderCreator().longValue())
                continue;

            if(!isOrderExecutable(sellOrder))
                continue;

            ListingOwner orderListingOwner = listingOwnerRepository.findByOwnerAndTicker(order.getOrderCreator(), order.getTicker())
                    .orElse(new ListingOwner(order.getOrderCreator(), order.getTicker(), 0));

            ListingOwner sellOrderListingOwner = listingOwnerRepository.findByOwnerAndTicker(sellOrder.getOrderCreator(), sellOrder.getTicker())
                    .orElseThrow();

            if(sellOrder.getQuantity() >= order.getQuantity()) {
                Integer quantity = order.getQuantity();
                orderListingOwner.setQuantity(orderListingOwner.getQuantity() + quantity);
                sellOrderListingOwner.setQuantity(sellOrderListingOwner.getQuantity() - quantity);
                sellOrder.setQuantity(sellOrder.getQuantity() - quantity);
                order.setQuantity(0);
                orderRepository.save(sellOrder);
                orderRepository.save(order);
                listingOwnerRepository.save(orderListingOwner);
                listingOwnerRepository.save(sellOrderListingOwner);
                updateBankAccountBalance(orderListingOwner.getOwner(), sellOrderListingOwner.getOwner(), buyPrice * quantity,
                        sellPrice * quantity);
                return;
            }
        }
    }

    private void checkBuyOrders(Order order, Double buyPrice, Double sellPrice) {
        List<Order> buyOrders = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
                order.getTicker(), OrderAction.BUY, 0);

        for(Order buyOrder: buyOrders) {
            if(order.getOrderCreator().longValue() == buyOrder.getOrderCreator().longValue())
                continue;

            if(!isOrderExecutable(buyOrder))
                continue;

            ListingOwner buyOrderListingOwner = listingOwnerRepository.findByOwnerAndTicker(buyOrder.getOrderCreator(),
                            buyOrder.getTicker()).orElse(new ListingOwner(buyOrder.getOrderCreator(), buyOrder.getTicker(), 0));

            ListingOwner orderListingOwner = listingOwnerRepository.findByOwnerAndTicker(order.getOrderCreator(), order.getTicker())
                    .orElseThrow();

            if(buyOrder.getQuantity() >= order.getQuantity()) {
                Integer quantity = order.getQuantity();
                orderListingOwner.setQuantity(orderListingOwner.getQuantity() - quantity);
                buyOrderListingOwner.setQuantity(buyOrderListingOwner.getQuantity() + quantity);
                buyOrder.setQuantity(buyOrder.getQuantity() - quantity);
                order.setQuantity(0);
                orderRepository.save(buyOrder);
                orderRepository.save(order);
                listingOwnerRepository.save(orderListingOwner);
                listingOwnerRepository.save(buyOrderListingOwner);
                updateBankAccountBalance(buyOrderListingOwner.getOwner(), orderListingOwner.getOwner(), buyPrice * quantity,
                        sellPrice * quantity);
                return;
            }
        }
    }

}
