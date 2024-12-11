package rs.edu.raf.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import rs.edu.raf.exceptions.PlaceOrderException;
import rs.edu.raf.mapper.OrderMapper;
import rs.edu.raf.model.ListingOwner;
import rs.edu.raf.model.Stock;
import rs.edu.raf.model.order.Order;
import rs.edu.raf.model.order.OrderAction;
import rs.edu.raf.repository.ListingOwnerRepository;
import rs.edu.raf.repository.OrderRepository;
import rs.edu.raf.repository.StockRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private ListingOwnerRepository listingOwnerRepository;
    @Mock
    private RestTemplate bankServiceRestTemplate;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private Order order;
    @Mock
    private Stock stock;
    @Mock
    private ListingOwner listingOwner;
    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        when(order.getTicker()).thenReturn("AAPL");
        when(order.getOrderAction()).thenReturn(OrderAction.BUY);
        when(order.getQuantity()).thenReturn(10);
        when(order.getOrderCreator()).thenReturn(1L);

        when(stockRepository.findById("AAPL")).thenReturn(Optional.of(stock));
    }

    @Test
    void placeOrder_successfulBuyOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        when(bankServiceRestTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity<>(true, HttpStatus.OK));

        orderService.placeOrder(order);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void placeOrder_failedBuyOrder_insufficientFunds() {
        when(bankServiceRestTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(new ResponseEntity<>(false, HttpStatus.OK));

        assertThrows(PlaceOrderException.class, () -> orderService.placeOrder(order));
    }

    @Test
    void placeOrder_successfulSellOrder() {
        when(listingOwner.getQuantity()).thenReturn(50);
        when(listingOwnerRepository.findByOwnerAndTicker(anyLong(), anyString())).thenReturn(Optional.of(listingOwner));
        when(orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(anyString(), any(OrderAction.class), 0))
                .thenReturn(List.of());

        when(order.getOrderAction()).thenReturn(OrderAction.SELL);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        orderService.placeOrder(order);

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void placeOrder_failedSellOrder_notEnoughStock() {
        when(listingOwner.getQuantity()).thenReturn(5);
        when(listingOwnerRepository.findByOwnerAndTicker(anyLong(), anyString())).thenReturn(Optional.of(listingOwner));
        when(orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(anyString(), any(OrderAction.class), 0))
                .thenReturn(List.of());

        when(order.getOrderAction()).thenReturn(OrderAction.SELL);
        when(order.getQuantity()).thenReturn(10);

        assertThrows(PlaceOrderException.class, () -> orderService.placeOrder(order));
    }
}