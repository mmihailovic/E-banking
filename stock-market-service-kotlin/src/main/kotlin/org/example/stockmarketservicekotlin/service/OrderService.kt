package org.example.stockmarketservicekotlin.service

import org.example.stockmarketservicekotlin.dto.*
import org.example.stockmarketservicekotlin.model.order.Order

interface OrderService {
    /**
     * Retrieves all orders
     * @return List of [OrderDTO] represents orders
     */
    fun getAllOrders(): List<OrderDTO>

    /**
     * Creates order
     * @param marketOrderCreateDTO DTO object contains information about order
     * @return [OrderDTO] object represents created market order
     */
    fun createMarketOrder(marketOrderCreateDTO: MarketOrderCreateDTO): OrderDTO

    /**
     * Creates order
     * @param limitOrderCreateDTO DTO object contains information about order
     * @return [OrderDTO] object represents created limit order
     */
    fun createLimitOrder(limitOrderCreateDTO: LimitOrderCreateDTO): OrderDTO

    /**
     * Creates order
     * @param stopOrderCreateDTO DTO object contains information about order
     * @return [OrderDTO] object represents created stop order
     */
    fun createStopOrder(stopOrderCreateDTO: StopOrderCreateDTO): OrderDTO

    /**
     * Creates order
     * @param stopLimitOrderCreateDTO DTO object contains information about order
     * @return [OrderDTO] object represents created stop limit order
     */
    fun createStopLimitOrder(stopLimitOrderCreateDTO: StopLimitOrderCreateDTO): OrderDTO

    /**
     * Creates order
     * @param allOrNoneOrderCreateDTO DTO object contains information about order
     * @return [OrderDTO] object represents created order
     */
    fun createAllOrNoneOrder(allOrNoneOrderCreateDTO: AllOrNoneOrderCreateDTO): OrderDTO

    /**
     * Place order
     * @param order the order to place
     * @return [OrderDTO] object represents placed order
     */
    fun placeOrder(order: Order): OrderDTO

    /**
     * Checks if sell order can be placed
     * @param sellOrder the sell order to be placed
     * @return true if sell order can be placed, otherwise false
     */
    fun canPlaceSellOrder(sellOrder: Order): Boolean

    /**
     * Deduct balance for given order
     * @param order the order
     * @param amount amount of money
     * @return true if the balance was successfully deducted, otherwise false
     */
    fun deductBalanceForOrder(order: Order, amount: Double): Boolean

    /**
     * Processes all orders
     */
    fun processOrders()
}