package rs.edu.raf.strategy;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import rs.edu.raf.model.ListingOwner;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessStrategyTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ListingOwnerRepository listingOwnerRepository;

    @InjectMocks
    private MarketOrderProcessStrategy marketOrderProcessStrategy;

    @Mock
    private Order buyOrder;

    @Mock
    private Order sellOrder;

    @Mock
    private ListingOwner buyListingOwner;

    @Mock
    private ListingOwner sellListingOwner;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private Gson gson;

    @Test
    void processBuyOrder_Successful() {
        when(buyOrder.getTicker()).thenReturn("AAPL");
        when(buyOrder.getQuantity()).thenReturn(10);
        when(buyOrder.getOrderCreator()).thenReturn(1L);

        when(sellOrder.getTicker()).thenReturn("AAPL");
        when(sellOrder.getQuantity()).thenReturn(5);
        when(sellOrder.getOrderCreator()).thenReturn(2L);

        when(orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(anyString(), eq(OrderAction.SELL), 0))
                .thenReturn(List.of(sellOrder));

        when(listingOwnerRepository.findByOwnerAndTicker(1L, "AAPL")).thenReturn(java.util.Optional.of(buyListingOwner));
        when(listingOwnerRepository.findByOwnerAndTicker(2L, "AAPL")).thenReturn(java.util.Optional.of(sellListingOwner));

        marketOrderProcessStrategy.processBuyOrder(buyOrder, 150.0, 150.0);

        verify(orderRepository, times(2)).save(any(Order.class));
    }

    @Test
    void processBuyOrder_Successful_MoreSells() {
        when(buyOrder.getTicker()).thenReturn("AAPL");
        when(buyOrder.getQuantity()).thenReturn(10);
        when(buyOrder.getOrderCreator()).thenReturn(1L);

        when(sellOrder.getTicker()).thenReturn("AAPL");
        when(sellOrder.getQuantity()).thenReturn(15);
        when(sellOrder.getOrderCreator()).thenReturn(2L);

        when(orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(anyString(), eq(OrderAction.SELL), 0))
                .thenReturn(List.of(sellOrder));
        Object object = null;
        doNothing().when(rabbitTemplate).convertAndSend(eq(null), eq(null), eq(object));
        when(listingOwnerRepository.findByOwnerAndTicker(1L, "AAPL")).thenReturn(java.util.Optional.of(buyListingOwner));
        when(listingOwnerRepository.findByOwnerAndTicker(2L, "AAPL")).thenReturn(java.util.Optional.of(sellListingOwner));
        marketOrderProcessStrategy.processBuyOrder(buyOrder, 150.0, 150.0);

        verify(orderRepository, times(2)).save(any(Order.class));
    }

    @Test
    void processSellOrder_Successful() {
        when(buyOrder.getTicker()).thenReturn("AAPL");
        when(buyOrder.getQuantity()).thenReturn(10);
        when(buyOrder.getOrderCreator()).thenReturn(1L);

        when(sellOrder.getTicker()).thenReturn("AAPL");
        when(sellOrder.getQuantity()).thenReturn(5);
        when(sellOrder.getOrderCreator()).thenReturn(2L);

        when(listingOwnerRepository.findByOwnerAndTicker(1L, "AAPL")).thenReturn(java.util.Optional.of(buyListingOwner));
        when(listingOwnerRepository.findByOwnerAndTicker(2L, "AAPL")).thenReturn(java.util.Optional.of(sellListingOwner));

        when(orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(sellOrder.getTicker(), OrderAction.BUY, 0))
                .thenReturn(List.of(buyOrder));

        marketOrderProcessStrategy.processSellOrder(sellOrder, 150.0, 150.0);

        verify(orderRepository, times(2)).save(any(Order.class));
    }

}