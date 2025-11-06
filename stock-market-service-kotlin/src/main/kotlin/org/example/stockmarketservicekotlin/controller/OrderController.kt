package org.example.stockmarketservicekotlin.controller

import jakarta.validation.Valid
import org.example.stockmarketservicekotlin.dto.*
import org.example.stockmarketservicekotlin.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/order")
class OrderController(private val orderService: OrderService) {
    @PostMapping("/market")
    fun createOrder(@RequestBody marketOrderCreateDTO: @Valid MarketOrderCreateDTO): ResponseEntity<OrderDTO> {
        return ResponseEntity<OrderDTO>(orderService.createMarketOrder(marketOrderCreateDTO), HttpStatus.CREATED)
    }

    @PostMapping("/limit")
    fun createLimitOrder(@RequestBody limitOrderCreateDTO: @Valid LimitOrderCreateDTO): ResponseEntity<OrderDTO> {
        return ResponseEntity<OrderDTO>(orderService.createLimitOrder(limitOrderCreateDTO), HttpStatus.CREATED)
    }

    @PostMapping("/stop")
    fun createStopOrder(@RequestBody stopOrderCreateDTO: @Valid StopOrderCreateDTO): ResponseEntity<OrderDTO> {
        return ResponseEntity<OrderDTO>(orderService.createStopOrder(stopOrderCreateDTO), HttpStatus.CREATED)
    }

    @PostMapping("/stop-limit")
    fun createStopLimitOrder(@RequestBody stopLimitOrderCreateDTO: @Valid StopLimitOrderCreateDTO): ResponseEntity<OrderDTO> {
        return ResponseEntity<OrderDTO>(orderService.createStopLimitOrder(stopLimitOrderCreateDTO), HttpStatus.CREATED)
    }

    @PostMapping("/all-or-none")
    fun createAllOrNoneOrder(@RequestBody allOrNoneOrderCreateDTO: @Valid AllOrNoneOrderCreateDTO): ResponseEntity<OrderDTO> {
        return ResponseEntity<OrderDTO>(orderService.createAllOrNoneOrder(allOrNoneOrderCreateDTO), HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllOrders(): ResponseEntity<List<OrderDTO>> {
        return ResponseEntity<List<OrderDTO>>(orderService.getAllOrders(), HttpStatus.OK)
    }
}