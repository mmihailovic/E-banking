package org.example.stockmarketservicekotlin.repository

import org.example.stockmarketservicekotlin.model.order.Order
import org.example.stockmarketservicekotlin.model.order.OrderAction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findAllByTickerAndOrderActionAndQuantityGreaterThan(
        ticker: String, orderAction: OrderAction, quantity: Int
    ): List<Order>

    fun findAllByQuantityGreaterThan(quantity: Int): List<Order>
}