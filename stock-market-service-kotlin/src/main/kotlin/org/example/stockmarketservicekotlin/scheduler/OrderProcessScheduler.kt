package org.example.stockmarketservicekotlin.scheduler

import org.example.stockmarketservicekotlin.service.OrderService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OrderProcessScheduler(private val orderService: OrderService) {
    @Scheduled(fixedDelay = 60000)
    fun processOrders() {
        orderService.processOrders()
    }
}