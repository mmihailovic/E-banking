package org.example.stockmarketservicekotlin.strategy

import org.example.stockmarketservicekotlin.model.order.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class OrderProcessStrategyFactory(
    @Qualifier("marketOrder")
    private val marketOrderProcessStrategy: OrderProcessStrategy,


    @Qualifier("stopOrder")
    private val stopOrderProcessStrategy: OrderProcessStrategy,


    @Qualifier("stopLimitOrder")
    private val stopLimitOrderProcessStrategy: OrderProcessStrategy,


    @Qualifier("allOrNoneOrder")
    private val allOrNoneOrderProcessStrategy: OrderProcessStrategy,


    @Qualifier("limitOrder")
    private val limitOrderProcessStrategy: OrderProcessStrategy,
) {
    fun getExecutionStrategy(order: Order): OrderProcessStrategy {
        if (order is MarkerOrder) {
            return marketOrderProcessStrategy
        }

        if (order is StopOrder) {
            return stopOrderProcessStrategy
        }

        if (order is StopLimitOrder) {
            return stopLimitOrderProcessStrategy
        }

        if (order is AllOrNoneOrder) {
            return allOrNoneOrderProcessStrategy
        }

        if (order is LimitOrder) {
            return limitOrderProcessStrategy
        }

        throw RuntimeException()
    }
}