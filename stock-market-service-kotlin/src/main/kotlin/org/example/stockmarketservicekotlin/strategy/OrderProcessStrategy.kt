package org.example.stockmarketservicekotlin.strategy

import org.example.stockmarketservicekotlin.model.order.Order

interface OrderProcessStrategy {
    /**
     * Process single order
     * @param order the order to process
     */
    fun processOrder(order: Order)
}