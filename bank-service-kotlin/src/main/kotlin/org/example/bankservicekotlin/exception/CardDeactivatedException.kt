package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CardDeactivatedException(val cardNumber: String) :
    CustomException("Card with number $cardNumber is deactivated!", HttpStatus.BAD_REQUEST)
