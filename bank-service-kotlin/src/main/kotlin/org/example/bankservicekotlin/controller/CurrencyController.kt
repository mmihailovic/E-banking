package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.CurrencyCreateDTO
import org.example.bankservicekotlin.dto.CurrencyDTO
import org.example.bankservicekotlin.service.CurrencyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/currency")
class CurrencyController(private val currencyService: CurrencyService) {
    @GetMapping
    fun getAllCurrencies(): ResponseEntity<List<CurrencyDTO>> {
        return ResponseEntity<List<CurrencyDTO>>(currencyService.getAllCurrencies(), HttpStatus.OK)
    }

    @PostMapping
    fun createCurrency(@RequestBody currencyCreateDTO: @Valid CurrencyCreateDTO): ResponseEntity<CurrencyDTO> {
        return ResponseEntity<CurrencyDTO>(currencyService.createCurrency(currencyCreateDTO), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCurrency(@PathVariable("id") id: Long): ResponseEntity<*> {
        currencyService.deleteCurrency(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}