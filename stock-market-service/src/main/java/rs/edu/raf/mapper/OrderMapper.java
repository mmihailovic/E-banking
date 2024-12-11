package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.*;
import rs.edu.raf.model.order.*;
import rs.edu.raf.security.JwtUtil;

@Component
@AllArgsConstructor
public class OrderMapper {
    private JwtUtil jwtUtil;

    public Order marketOrderCreateDTOtoOrder(MarketOrderCreateDTO marketOrderCreateDTO) {
        MarketOrder order = new MarketOrder();

        order.setQuantity(marketOrderCreateDTO.quantity());
        order.setTicker(marketOrderCreateDTO.ticker());
        order.setOrderAction(OrderAction.valueOf(marketOrderCreateDTO.orderAction()));
        order.setOrderCreator(jwtUtil.getOrderCreator());

        return order;
    }

    public Order limitOrderCreateDTOtoOrder(LimitOrderCreateDTO limitOrderCreateDTO) {
        LimitOrder order = new LimitOrder();

        order.setQuantity(limitOrderCreateDTO.quantity());
        order.setTicker(limitOrderCreateDTO.ticker());
        order.setOrderAction(OrderAction.valueOf(limitOrderCreateDTO.orderAction()));
        order.setLimitAmount(limitOrderCreateDTO.limit());
        order.setOrderCreator(jwtUtil.getOrderCreator());

        return order;
    }

    public Order stopOrderCreateDTOtoOrder(StopOrderCreateDTO stopOrderCreateDTO) {
        StopOrder order = new StopOrder();

        order.setQuantity(stopOrderCreateDTO.quantity());
        order.setTicker(stopOrderCreateDTO.ticker());
        order.setOrderAction(OrderAction.valueOf(stopOrderCreateDTO.orderAction()));
        order.setStop(stopOrderCreateDTO.stop());
        order.setOrderCreator(jwtUtil.getOrderCreator());

        return order;
    }

    public Order stopLimitOrderCreateDTOtoOrder(StopLimitOrderCreateDTO stopLimitOrderCreateDTO) {
        StopLimitOrder order = new StopLimitOrder();

        order.setQuantity(stopLimitOrderCreateDTO.quantity());
        order.setTicker(stopLimitOrderCreateDTO.ticker());
        order.setOrderAction(OrderAction.valueOf(stopLimitOrderCreateDTO.orderAction()));
        order.setStop(stopLimitOrderCreateDTO.stop());
        order.setLimitAmount(stopLimitOrderCreateDTO.limit());
        order.setOrderCreator(jwtUtil.getOrderCreator());

        return order;
    }

    public Order allOrNoneOrderCreateDTOtoOrder(AllOrNoneOrderCreateDTO allOrNoneOrderCreateDTO) {
        StopLimitOrder order = new StopLimitOrder();

        order.setQuantity(allOrNoneOrderCreateDTO.quantity());
        order.setTicker(allOrNoneOrderCreateDTO.ticker());
        order.setOrderAction(OrderAction.valueOf(allOrNoneOrderCreateDTO.orderAction()));
        order.setOrderCreator(jwtUtil.getOrderCreator());

        return order;
    }


    public OrderDTO orderToOrderDTO(Order order) {
        return new OrderDTO(order.getId(), order.getOrderAction().toString(), order.getQuantity(), order.getTicker());
    }
}
