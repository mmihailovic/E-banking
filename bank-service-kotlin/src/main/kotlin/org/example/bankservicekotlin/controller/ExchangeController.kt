package org.example.bankservicekotlin.controller

import org.example.bankservicekotlin.dto.ExchangeInvoiceDTO
import org.example.bankservicekotlin.dto.ExchangeRateDTO
import org.example.bankservicekotlin.service.ExchangeInvoiceService
import org.example.bankservicekotlin.service.ExchangeRateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kotlin/exchange")
class ExchangeController(
    private val exchangeRateService: ExchangeRateService,
    private val exchangeInvoiceService: ExchangeInvoiceService
) {
    @GetMapping
    fun getAllCurrencyRates(): ResponseEntity<List<ExchangeRateDTO>> {
        return ResponseEntity<List<ExchangeRateDTO>>(exchangeRateService.getAllExchangeRates(), HttpStatus.OK)
    }

    @GetMapping("/invoices/{currency}")
    fun allInvoicesByCurrency(@PathVariable("currency") currency: String): ResponseEntity<List<ExchangeInvoiceDTO>> {
        return ResponseEntity<List<ExchangeInvoiceDTO>>(
            exchangeInvoiceService.listInvoicesByCurrency(currency),
            HttpStatus.OK
        )
    }

}