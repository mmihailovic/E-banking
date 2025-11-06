package org.example.stockmarketservicekotlin.model.order

import jakarta.persistence.Entity

@Entity
class LimitOrder(
    id: Long?,
    orderAction: OrderAction,
    ticker: String,
    quantity: Int,
    orderCreator: Long,
    val limitAmount: Double
) :
    Order(id, orderAction, ticker, quantity, orderCreator) {
}