package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class ExchangeRateNotFoundException(val currencyCode: String) :
    CustomException("Exchange rate for currency with code $currencyCode not found!", HttpStatus.NOT_FOUND)
