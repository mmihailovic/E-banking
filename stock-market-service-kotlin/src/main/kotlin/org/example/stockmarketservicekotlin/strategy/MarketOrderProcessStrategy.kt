package org.example.stockmarketservicekotlin.strategy

import com.google.gson.Gson
import org.example.stockmarketservicekotlin.exceptions.StockDoesntExistException
import org.example.stockmarketservicekotlin.model.Stock
import org.example.stockmarketservicekotlin.model.order.Order
import org.example.stockmarketservicekotlin.model.order.OrderAction
import org.example.stockmarketservicekotlin.repository.ListingOwnerRepository
import org.example.stockmarketservicekotlin.repository.OrderRepository
import org.example.stockmarketservicekotlin.repository.StockRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component("marketOrder")
class MarketOrderProcessStrategy(
    @Value("\${update.balance.exchange}") UPDATE_BALANCE_EXCHANGE: String,
    @Value("\${update.balance.routing.key}") UPDATE_BALANCE_ROUTING_KEY: String,
    rabbitTemplate: RabbitTemplate,
    gson: Gson,
    orderRepository: OrderRepository,
    private val stockRepository: StockRepository,
    listingOwnerRepository: ListingOwnerRepository,
) : AbstractOrderProcessStrategy(
    UPDATE_BALANCE_EXCHANGE, UPDATE_BALANCE_ROUTING_KEY, orderRepository, listingOwnerRepository, rabbitTemplate, gson
) {
    override fun processOrder(order: Order) {
        if (!isOrderExecutable(order)) {
            return
        }
        val ticker: String = order.ticker
        val stock: Stock = stockRepository.findById(ticker).orElseThrow { StockDoesntExistException(ticker) }

        if (order.orderAction == OrderAction.BUY) {
            processBuyOrder(order, stock.high, stock.low)
        } else {
            processSellOrder(order, stock.high, stock.low)
        }
    }

    public override fun isOrderExecutable(order: Order): Boolean {
        return true
    }
}