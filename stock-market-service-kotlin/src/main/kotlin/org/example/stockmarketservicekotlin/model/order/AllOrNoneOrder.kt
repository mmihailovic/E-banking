package org.example.stockmarketservicekotlin.model.order

import jakarta.persistence.Entity

@Entity
class AllOrNoneOrder(id: Long?, orderAction: OrderAction, ticker: String, quantity: Int, orderCreator: Long) :
    Order(id, orderAction, ticker, quantity, orderCreator) {
}