package org.example.stockmarketservicekotlin.model.order

import jakarta.persistence.Entity

@Entity
class StopOrder(
    id: Long?,
    orderAction: OrderAction,
    ticker: String,
    quantity: Int,
    orderCreator: Long,
    val stop: Double
) :
    Order(id, orderAction, ticker, quantity, orderCreator) {
}