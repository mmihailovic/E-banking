package rs.edu.raf.strategy;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.UpdateBalanceDTO;
import rs.edu.raf.model.ListingOwner;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;

import java.util.List;

@Component
public abstract class AbstractOrderProcessStrategy implements OrderProcessStrategy{
    private String UPDATE_BALANCE_EXCHANGE;
    private String UPDATE_BALANCE_ROUTING_KEY;
    private OrderRepository orderRepository;
    private ListingOwnerRepository listingOwnerRepository;
    private RabbitTemplate rabbitTemplate;
    private Gson gson;

    public AbstractOrderProcessStrategy(@Value("${update.balance.exchange}") String UPDATE_BALANCE_EXCHANGE,
                                        @Value("${update.balance.routing.key}") String UPDATE_BALANCE_ROUTING_KEY,
                                        OrderRepository orderRepository, ListingOwnerRepository listingOwnerRepository,
                                        RabbitTemplate rabbitTemplate, Gson gson) {
        this.UPDATE_BALANCE_EXCHANGE = UPDATE_BALANCE_EXCHANGE;
        this.UPDATE_BALANCE_ROUTING_KEY = UPDATE_BALANCE_ROUTING_KEY;
        this.orderRepository = orderRepository;
        this.listingOwnerRepository = listingOwnerRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.gson = gson;
    }

    /**
     * Checks if the order is executable
     * @param order the order
     * @return true if the order is executable, otherwise false
     */
    protected abstract boolean isOrderExecutable(Order order);

    protected void processBuyOrder(Order buyOrder, Double buyPrice, Double sellPrice) {

        List<Order> sellOrders = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
                buyOrder.getTicker(), OrderAction.SELL, 0);

        for(Order sellOrder: sellOrders) {
            if(buyOrder.getOrderCreator().longValue() == sellOrder.getOrderCreator().longValue())
                continue;

            if(!isOrderExecutable(sellOrder))
                continue;

            executeTransaction(buyOrder, sellOrder, buyPrice, sellPrice);

            if(buyOrder.getQuantity() == 0) {
                return;
            }
        }
    }

    protected void processSellOrder(Order sellOrder, Double buyPrice, Double sellPrice) {
        List<Order> buyOrders = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
                sellOrder.getTicker(), OrderAction.BUY, 0);

        for(Order buyOrder: buyOrders) {
            if(sellOrder.getOrderCreator().longValue() == buyOrder.getOrderCreator().longValue())
                continue;

            if(!isOrderExecutable(buyOrder))
                continue;

            executeTransaction(buyOrder, sellOrder, buyPrice, sellPrice);

            if(sellOrder.getQuantity() == 0) {
                return;
            }
        }
    }

    @Transactional
    protected void executeTransaction(Order buyOrder, Order sellOrder, Double buyPrice, Double sellPrice) {
        ListingOwner buyListingOwner = listingOwnerRepository.findByOwnerAndTicker(buyOrder.getOrderCreator(), buyOrder.getTicker())
                .orElse(new ListingOwner(buyOrder.getOrderCreator(), buyOrder.getTicker(), 0));

        ListingOwner sellListingOwner = listingOwnerRepository.findByOwnerAndTicker(sellOrder.getOrderCreator(), sellOrder.getTicker())
                .orElseThrow();

        if(sellOrder.getQuantity() <= buyOrder.getQuantity()) {
            Integer quantity = sellOrder.getQuantity();
            buyListingOwner.setQuantity(buyListingOwner.getQuantity() + quantity);
            sellListingOwner.setQuantity(sellListingOwner.getQuantity() - quantity);
            buyOrder.setQuantity(buyOrder.getQuantity() - quantity);
            sellOrder.setQuantity(0);
            updateBankAccountBalance(buyListingOwner.getOwner(), sellListingOwner.getOwner(), buyPrice * quantity,
                    sellPrice * quantity);
        }
        else {
            Integer quantity = buyOrder.getQuantity();
            buyListingOwner.setQuantity(buyListingOwner.getQuantity() + quantity);
            sellListingOwner.setQuantity(sellListingOwner.getQuantity() - quantity);
            sellOrder.setQuantity(sellOrder.getQuantity() - quantity);
            buyOrder.setQuantity(0);
            updateBankAccountBalance(buyListingOwner.getOwner(), sellListingOwner.getOwner(), buyPrice * quantity,
                    sellPrice * quantity);
        }
        orderRepository.save(sellOrder);
        orderRepository.save(buyOrder);
        listingOwnerRepository.save(buyListingOwner);
        listingOwnerRepository.save(sellListingOwner);
    }

    protected void updateBankAccountBalance(Long buyerId, Long sellerId, Double buyPrice, Double sellPrice) {
        UpdateBalanceDTO updateBalanceDTO = new UpdateBalanceDTO(buyerId, sellerId, buyPrice, sellPrice);
        rabbitTemplate.convertAndSend(UPDATE_BALANCE_EXCHANGE, UPDATE_BALANCE_ROUTING_KEY, gson.toJson(updateBalanceDTO));
    }
}
