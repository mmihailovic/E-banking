package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CardNotFoundException(val cardNumber: String) :
    CustomException("Card with number $cardNumber not found!", HttpStatus.NOT_FOUND)
