package rs.edu.raf.service;

import rs.edu.raf.dto.*;
import rs.edu.raf.model.order.Order;

import java.util.List;

public interface OrderService {

    /**
     * Retrieves all orders
     * @return List of {@link OrderDTO} represents orders
     */
    List<OrderDTO> getAllOrders();

    /**
     * Creates order
     * @param marketOrderCreateDTO DTO object contains information about order
     * @return {@link OrderDTO} object represents created market order
     */
    OrderDTO createMarketOrder(MarketOrderCreateDTO marketOrderCreateDTO);

    /**
     * Creates order
     * @param limitOrderCreateDTO DTO object contains information about order
     * @return {@link OrderDTO} object represents created limit order
     */
    OrderDTO createLimitOrder(LimitOrderCreateDTO limitOrderCreateDTO);

    /**
     * Creates order
     * @param stopOrderCreateDTO DTO object contains information about order
     * @return {@link OrderDTO} object represents created stop order
     */
    OrderDTO createStopOrder(StopOrderCreateDTO stopOrderCreateDTO);

    /**
     * Creates order
     * @param stopLimitOrderCreateDTO DTO object contains information about order
     * @return {@link OrderDTO} object represents created stop limit order
     */
    OrderDTO createStopLimitOrder(StopLimitOrderCreateDTO stopLimitOrderCreateDTO);

    /**
     * Creates order
     * @param allOrNoneOrderCreateDTO DTO object contains information about order
     * @return {@link OrderDTO} object represents created order
     */
    OrderDTO createAllOrNoneOrder(AllOrNoneOrderCreateDTO allOrNoneOrderCreateDTO);

    /**
     * Place order
     * @param order the order to place
     * @return {@link OrderDTO} object represents placed order
     */
    OrderDTO placeOrder(Order order);

    /**
     * Checks if sell order can be placed
     * @param sellOrder the sell order to be placed
     * @return true if sell order can be placed, otherwise false
     */
    boolean canPlaceSellOrder(Order sellOrder);

    /**
     * Deduct balance for given order
     * @param order the order
     * @param amount amount of money
     * @return true if the balance was successfully deducted, otherwise false
     */
    boolean deductBalanceForOrder(Order order, Double amount);

    /**
     * Processes all orders
     */
    void processOrders();
}
