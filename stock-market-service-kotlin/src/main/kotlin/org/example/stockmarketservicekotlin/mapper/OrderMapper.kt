package org.example.stockmarketservicekotlin.mapper

import org.example.stockmarketservicekotlin.model.order.*
import org.example.stockmarketservicekotlin.dto.*
import org.example.stockmarketservicekotlin.security.JwtUtil

import org.springframework.stereotype.Component

@Component
class OrderMapper(private val jwtUtil: JwtUtil) {
    fun marketOrderCreateDTOtoOrder(marketOrderCreateDTO: MarketOrderCreateDTO): Order {
        val orderAction = OrderAction.valueOf(marketOrderCreateDTO.orderAction)
        return MarkerOrder(
            null,
            orderAction,
            marketOrderCreateDTO.ticker,
            marketOrderCreateDTO.quantity,
            jwtUtil.getIDForLoggedUser()!!
        )
    }

    fun limitOrderCreateDTOtoOrder(limitOrderCreateDTO: LimitOrderCreateDTO): Order {
        val orderAction = OrderAction.valueOf(limitOrderCreateDTO.orderAction)
        return LimitOrder(
            null,
            orderAction,
            limitOrderCreateDTO.ticker,
            limitOrderCreateDTO.quantity,
            jwtUtil.getIDForLoggedUser()!!,
            limitOrderCreateDTO.limit
        )
    }

    fun stopOrderCreateDTOtoOrder(stopOrderCreateDTO: StopOrderCreateDTO): Order {
        val orderAction = OrderAction.valueOf(stopOrderCreateDTO.orderAction)
        return StopOrder(
            null,
            orderAction,
            stopOrderCreateDTO.ticker,
            stopOrderCreateDTO.quantity,
            jwtUtil.getIDForLoggedUser()!!,
            stopOrderCreateDTO.stop
        )
    }

    fun stopLimitOrderCreateDTOtoOrder(stopLimitOrderCreateDTO: StopLimitOrderCreateDTO): Order {
        val orderAction = OrderAction.valueOf(stopLimitOrderCreateDTO.orderAction)
        return StopLimitOrder(
            null,
            orderAction,
            stopLimitOrderCreateDTO.ticker,
            stopLimitOrderCreateDTO.quantity,
            jwtUtil.getIDForLoggedUser()!!,
            stopLimitOrderCreateDTO.stop,
            stopLimitOrderCreateDTO.limit
        )
    }

    fun allOrNoneOrderCreateDTOtoOrder(allOrNoneOrderCreateDTO: AllOrNoneOrderCreateDTO): Order {
        val orderAction = OrderAction.valueOf(allOrNoneOrderCreateDTO.orderAction)
        return AllOrNoneOrder(
            null,
            orderAction,
            allOrNoneOrderCreateDTO.ticker,
            allOrNoneOrderCreateDTO.quantity,
            jwtUtil.getIDForLoggedUser()!!
        )
    }


    fun orderToOrderDTO(order: Order): OrderDTO {
        return OrderDTO(order.id, order.orderAction.toString(), order.quantity, order.ticker)
    }
}