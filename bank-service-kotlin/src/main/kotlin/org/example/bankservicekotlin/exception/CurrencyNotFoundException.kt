package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CurrencyNotFoundException(val id: Long) :
    CustomException("Currency with ID $id not found!", HttpStatus.NOT_FOUND)
