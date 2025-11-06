package org.example.stockmarketservicekotlin.strategy

import com.google.gson.Gson
import jakarta.transaction.Transactional
import org.example.stockmarketservicekotlin.dto.UpdateBalanceDTO
import org.example.stockmarketservicekotlin.model.ListingOwner
import org.example.stockmarketservicekotlin.model.order.Order
import org.example.stockmarketservicekotlin.model.order.OrderAction
import org.example.stockmarketservicekotlin.repository.ListingOwnerRepository
import org.example.stockmarketservicekotlin.repository.OrderRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
abstract class AbstractOrderProcessStrategy(
    @Value("\${update.balance.exchange}") private val UPDATE_BALANCE_EXCHANGE: String,
    @Value("\${update.balance.routing.key}") private val UPDATE_BALANCE_ROUTING_KEY: String,
    protected val orderRepository: OrderRepository,
    private val listingOwnerRepository: ListingOwnerRepository,
    private val rabbitTemplate: RabbitTemplate,
    private val gson: Gson
) : OrderProcessStrategy {

    /**
     * Checks if the order is executable
     * @param order the order
     * @return true if the order is executable, otherwise false
     */
    protected abstract fun isOrderExecutable(order: Order): Boolean

    protected fun processBuyOrder(buyOrder: Order, buyPrice: Double, sellPrice: Double) {
        val sellOrders: List<Order> = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
            buyOrder.ticker, OrderAction.SELL, 0
        )

        for (sellOrder in sellOrders) {
            if (buyOrder.orderCreator == sellOrder.orderCreator) continue

            if (!isOrderExecutable(sellOrder)) continue

            executeTransaction(buyOrder, sellOrder, buyPrice, sellPrice)

            if (buyOrder.quantity == 0) {
                return
            }
        }
    }

    protected fun processSellOrder(sellOrder: Order, buyPrice: Double, sellPrice: Double) {
        val buyOrders: List<Order> = orderRepository.findAllByTickerAndOrderActionAndQuantityGreaterThan(
            sellOrder.ticker, OrderAction.BUY, 0
        )

        for (buyOrder in buyOrders) {
            if (sellOrder.orderCreator == buyOrder.orderCreator) continue

            if (!isOrderExecutable(buyOrder)) continue

            executeTransaction(buyOrder, sellOrder, buyPrice, sellPrice)

            if (sellOrder.quantity == 0) {
                return
            }
        }
    }

    @Transactional
    protected fun executeTransaction(buyOrder: Order, sellOrder: Order, buyPrice: Double, sellPrice: Double) {
        val buyListingOwner: ListingOwner =
            listingOwnerRepository.findByOwnerAndTicker(buyOrder.orderCreator, buyOrder.ticker)
                .orElse(ListingOwner(null, buyOrder.orderCreator, buyOrder.ticker, 0))

        val sellListingOwner: ListingOwner =
            listingOwnerRepository.findByOwnerAndTicker(sellOrder.orderCreator, sellOrder.ticker)
                .orElseThrow()

        if (sellOrder.quantity <= buyOrder.quantity) {
            val quantity: Int = sellOrder.quantity
            buyListingOwner.quantity += quantity
            sellListingOwner.quantity -= quantity
            buyOrder.quantity -= quantity
            sellOrder.quantity = 0
            updateBankAccountBalance(
                buyListingOwner.owner, sellListingOwner.owner, buyPrice * quantity,
                sellPrice * quantity
            )
        } else {
            val quantity: Int = buyOrder.quantity
            buyListingOwner.quantity += quantity
            sellListingOwner.quantity -= quantity
            sellOrder.quantity -= quantity
            buyOrder.quantity = 0
            updateBankAccountBalance(
                buyListingOwner.owner, sellListingOwner.owner, buyPrice * quantity,
                sellPrice * quantity
            )
        }
        orderRepository.save(sellOrder)
        orderRepository.save(buyOrder)
        listingOwnerRepository.save(buyListingOwner)
        listingOwnerRepository.save(sellListingOwner)
    }

    protected fun updateBankAccountBalance(buyerId: Long, sellerId: Long, buyPrice: Double, sellPrice: Double) {
        val updateBalanceDTO: UpdateBalanceDTO = UpdateBalanceDTO(buyerId, sellerId, buyPrice, sellPrice)
        rabbitTemplate.convertAndSend(
            UPDATE_BALANCE_EXCHANGE,
            UPDATE_BALANCE_ROUTING_KEY,
            gson.toJson(updateBalanceDTO)
        )
    }
}