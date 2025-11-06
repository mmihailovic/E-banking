package org.example.stockmarketservicekotlin.strategy

import com.google.gson.Gson
import org.example.stockmarketservicekotlin.exceptions.StockDoesntExistException
import org.example.stockmarketservicekotlin.model.ListingOwner
import org.example.stockmarketservicekotlin.model.Stock
import org.example.stockmarketservicekotlin.model.order.Order
import org.example.stockmarketservicekotlin.model.order.OrderAction
import org.example.stockmarketservicekotlin.repository.ListingOwnerRepository
import org.example.stockmarketservicekotlin.repository.OrderRepository
import org.example.stockmarketservicekotlin.repository.StockRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component("allOrNoneOrder")
class AllOrNoneOrderProcessStrategy(
    @Value("\${update.balance.exchange}") UPDATE_BALANCE_EXCHANGE: String,
    @Value("\${update.balance.routing.key}") UPDATE_BALANCE_ROUTING_KEY: String,
    rabbitTemplate: RabbitTemplate,
    gson: Gson,
    orderRepository: OrderRepository,
    private val stockRepository: StockRepository,
    private val listingOwnerRepository: ListingOwnerRepository,

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
            checkSellOrders(order, stock.high, stock.low)
        } else {
            checkBuyOrders(order, stock.high, stock.low)
        }
    }

    override fun isOrderExecutable(order: Order): Boolean {
        return true
    }

    private fun checkSellOrders(order: Order, buyPrice: Double, sellPrice: Double) {
        val sellOrders: List<Order> = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
            order.ticker, OrderAction.SELL, 0
        )

        for (sellOrder in sellOrders) {
            if (order.orderCreator == sellOrder.orderCreator) continue

            if (!isOrderExecutable(sellOrder)) continue

            val orderListingOwner: ListingOwner =
                listingOwnerRepository.findByOwnerAndTicker(order.orderCreator, order.ticker)
                    .orElse(ListingOwner(null, order.orderCreator, order.ticker, 0))

            val sellOrderListingOwner: ListingOwner =
                listingOwnerRepository.findByOwnerAndTicker(sellOrder.orderCreator, sellOrder.ticker).orElseThrow()

            if (sellOrder.quantity >= order.quantity) {
                val quantity: Int = order.quantity
                orderListingOwner.quantity += quantity
                sellOrderListingOwner.quantity -= quantity
                sellOrder.quantity -= quantity
                order.quantity = 0
                orderRepository.save(sellOrder)
                orderRepository.save(order)
                listingOwnerRepository.save(orderListingOwner)
                listingOwnerRepository.save(sellOrderListingOwner)
                updateBankAccountBalance(
                    orderListingOwner.owner, sellOrderListingOwner.owner, buyPrice * quantity, sellPrice * quantity
                )
                return
            }
        }
    }

    private fun checkBuyOrders(order: Order, buyPrice: Double, sellPrice: Double) {
        val buyOrders: List<Order> = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
            order.ticker, OrderAction.BUY, 0
        )

        for (buyOrder in buyOrders) {
            if (order.orderCreator == buyOrder.orderCreator) continue

            if (!isOrderExecutable(buyOrder)) continue

            val buyOrderListingOwner: ListingOwner = listingOwnerRepository.findByOwnerAndTicker(
                buyOrder.orderCreator, buyOrder.ticker
            ).orElse(ListingOwner(null, buyOrder.orderCreator, buyOrder.ticker, 0))

            val orderListingOwner: ListingOwner =
                listingOwnerRepository.findByOwnerAndTicker(order.orderCreator, order.ticker).orElseThrow()

            if (buyOrder.quantity >= order.quantity) {
                val quantity: Int = order.quantity
                orderListingOwner.quantity -= quantity
                buyOrderListingOwner.quantity += quantity
                buyOrder.quantity -= quantity
                order.quantity = 0
                orderRepository.save(buyOrder)
                orderRepository.save(order)
                listingOwnerRepository.save(orderListingOwner)
                listingOwnerRepository.save(buyOrderListingOwner)
                updateBankAccountBalance(
                    buyOrderListingOwner.owner, orderListingOwner.owner, buyPrice * quantity, sellPrice * quantity
                )
                return
            }
        }
    }
}