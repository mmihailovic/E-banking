package org.example.stockmarketservicekotlin.service.impl

import org.example.stockmarketservicekotlin.dto.*
import org.example.stockmarketservicekotlin.exceptions.PlaceOrderException
import org.example.stockmarketservicekotlin.mapper.OrderMapper
import org.example.stockmarketservicekotlin.model.ListingOwner
import org.example.stockmarketservicekotlin.model.Stock
import org.example.stockmarketservicekotlin.model.order.Order
import org.example.stockmarketservicekotlin.model.order.OrderAction
import org.example.stockmarketservicekotlin.repository.ListingOwnerRepository
import org.example.stockmarketservicekotlin.repository.OrderRepository
import org.example.stockmarketservicekotlin.repository.StockRepository
import org.example.stockmarketservicekotlin.service.OrderService
import org.example.stockmarketservicekotlin.strategy.OrderProcessStrategy
import org.example.stockmarketservicekotlin.strategy.OrderProcessStrategyFactory
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper,
    private val orderProcessStrategyFactory: OrderProcessStrategyFactory,
    private val listingOwnerRepository: ListingOwnerRepository,
    private val bankServiceRestTemplate: RestTemplate,
    private val stockRepository: StockRepository,
) : OrderService {
    override fun getAllOrders(): List<OrderDTO> {
        return orderRepository.findAll().stream().map(orderMapper::orderToOrderDTO).toList()
    }

    override fun createMarketOrder(marketOrderCreateDTO: MarketOrderCreateDTO): OrderDTO {
        return placeOrder(orderMapper.marketOrderCreateDTOtoOrder(marketOrderCreateDTO))
    }

    override fun createLimitOrder(limitOrderCreateDTO: LimitOrderCreateDTO): OrderDTO {
        return placeOrder(orderMapper.limitOrderCreateDTOtoOrder(limitOrderCreateDTO))
    }

    override fun createStopOrder(stopOrderCreateDTO: StopOrderCreateDTO): OrderDTO {
        return placeOrder(orderMapper.stopOrderCreateDTOtoOrder(stopOrderCreateDTO))
    }

    override fun createStopLimitOrder(stopLimitOrderCreateDTO: StopLimitOrderCreateDTO): OrderDTO {
        return placeOrder(orderMapper.stopLimitOrderCreateDTOtoOrder(stopLimitOrderCreateDTO))
    }

    override fun createAllOrNoneOrder(allOrNoneOrderCreateDTO: AllOrNoneOrderCreateDTO): OrderDTO {
        return placeOrder(orderMapper.allOrNoneOrderCreateDTOtoOrder(allOrNoneOrderCreateDTO))
    }

    override fun deductBalanceForOrder(order: Order, amount: Double): Boolean {
        return bankServiceRestTemplate.exchange(
            ("/bank-accounts/deduct-available-balance/" + order.orderCreator + "/" + amount),
            HttpMethod.PUT,
            null,
            Boolean::class.java
        ).body ?: false
    }

    override fun processOrders() {
        for (order in orderRepository.findAllByQuantityGreaterThan(0)) {
            val orderProcessStrategy: OrderProcessStrategy = orderProcessStrategyFactory.getExecutionStrategy(order)
            orderProcessStrategy.processOrder(order)
        }
    }

    override fun placeOrder(order: Order): OrderDTO {
        val stock: Stock = stockRepository.findById(order.ticker).orElseThrow()

        val canPlaceBuyOrder =
            order.orderAction == OrderAction.BUY && deductBalanceForOrder(order, order.quantity * stock.price)

        val canPlaceSellOrder = order.orderAction === OrderAction.SELL && canPlaceSellOrder(order)

        if (canPlaceBuyOrder || canPlaceSellOrder) {
            return orderMapper.orderToOrderDTO(orderRepository.save(order))
        }

        throw PlaceOrderException(order.id)
    }

    override fun canPlaceSellOrder(order: Order): Boolean {
        val optionalListingOwner: Optional<ListingOwner> =
            listingOwnerRepository.findByOwnerAndTicker(order.orderCreator, order.ticker)

        if (optionalListingOwner.isEmpty) {
            return false
        }

        return orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
            order.ticker, order.orderAction, 0
        ).stream().map(Order::quantity)
            .reduce(0) { a: Int, b: Int -> Integer.sum(a, b) } + order.quantity <= optionalListingOwner.get().quantity
    }
}