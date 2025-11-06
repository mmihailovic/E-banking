package org.example.stockmarketservicekotlin.exceptions

import org.springframework.http.HttpStatus

data class PlaceOrderException(val id: Long?) :
    CustomException("Order with ID $id can't be placed!", HttpStatus.BAD_REQUEST)
