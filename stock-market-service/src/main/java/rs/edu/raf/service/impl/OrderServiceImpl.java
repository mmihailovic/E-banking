package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.edu.raf.dto.*;
import rs.edu.raf.exceptions.PlaceOrderException;
import rs.edu.raf.mapper.OrderMapper;
import rs.edu.raf.model.ListingOwner;
import rs.edu.raf.model.Stock;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;
import rs.edu.raf.repository.StockRepository;
import rs.edu.raf.service.OrderService;
import rs.edu.raf.strategy.OrderProcessStrategy;
import rs.edu.raf.strategy.OrderProcessStrategyFactory;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private OrderProcessStrategyFactory orderProcessStrategyFactory;
    private ListingOwnerRepository listingOwnerRepository;
    private RestTemplate bankServiceRestTemplate;
    private StockRepository stockRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::orderToOrderDTO).toList();
    }

    @Override
    public OrderDTO createMarketOrder(MarketOrderCreateDTO marketOrderCreateDTO) {
        return placeOrder(orderMapper.marketOrderCreateDTOtoOrder(marketOrderCreateDTO));
    }

    @Override
    public OrderDTO createLimitOrder(LimitOrderCreateDTO limitOrderCreateDTO) {
        return placeOrder(orderMapper.limitOrderCreateDTOtoOrder(limitOrderCreateDTO));
    }

    @Override
    public OrderDTO createStopOrder(StopOrderCreateDTO stopOrderCreateDTO) {
        return placeOrder(orderMapper.stopOrderCreateDTOtoOrder(stopOrderCreateDTO));
    }

    @Override
    public OrderDTO createStopLimitOrder(StopLimitOrderCreateDTO stopLimitOrderCreateDTO) {
        return placeOrder(orderMapper.stopLimitOrderCreateDTOtoOrder(stopLimitOrderCreateDTO));
    }

    @Override
    public OrderDTO createAllOrNoneOrder(AllOrNoneOrderCreateDTO allOrNoneOrderCreateDTO) {
        return placeOrder(orderMapper.allOrNoneOrderCreateDTOtoOrder(allOrNoneOrderCreateDTO));
    }

    @Override
    public boolean deductBalanceForOrder(Order order, Double amount) {
        return bankServiceRestTemplate.exchange("/bank-accounts/deduct-available-balance/" + order.getOrderCreator()
                        + "/" + amount, HttpMethod.PUT, null , Boolean.class).getBody();
    }

    @Override
    public void processOrders() {
        for (Order order: orderRepository.findAllByQuantityGreaterThan(0)) {
            OrderProcessStrategy orderProcessStrategy = orderProcessStrategyFactory.getExecutionStrategy(order);
            orderProcessStrategy.processOrder(order);
        }
    }

    @Override
    public OrderDTO placeOrder(Order order) {
        Stock stock = stockRepository.findById(order.getTicker()).orElseThrow();

        boolean canPlaceBuyOrder = order.getOrderAction() == OrderAction.BUY
                && deductBalanceForOrder(order, order.getQuantity() * stock.getPrice());

        boolean canPlaceSellOrder = order.getOrderAction() == OrderAction.SELL && canPlaceSellOrder(order);

        if(canPlaceBuyOrder || canPlaceSellOrder) {
            return orderMapper.orderToOrderDTO(orderRepository.save(order));
        }

        throw new PlaceOrderException();
    }

    @Override
    public boolean canPlaceSellOrder(Order order) {
        Optional<ListingOwner> optionalListingOwner =
                listingOwnerRepository.findByOwnerAndTicker(order.getOrderCreator(), order.getTicker());

        if(optionalListingOwner.isEmpty()) {
            return false;
        }

        return orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(order.getTicker(), order.getOrderAction(), 0)
                .stream()
                .map(Order::getQuantity)
                .reduce(0, Integer::sum) + order.getQuantity() <= optionalListingOwner.get().getQuantity();
    }
}
