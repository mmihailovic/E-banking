package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class ExchangeAccountNotFoundException(val currencyCode: String) :
    CustomException("Exchange account for currency with code $currencyCode not found!", HttpStatus.NOT_FOUND)
