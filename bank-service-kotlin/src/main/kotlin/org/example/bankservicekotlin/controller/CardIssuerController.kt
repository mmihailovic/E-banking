package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.CardIssuerCreateDTO
import org.example.bankservicekotlin.dto.CardIssuerDTO
import org.example.bankservicekotlin.service.CardIssuerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/card-issuer")
class CardIssuerController(private val cardIssuerService: CardIssuerService) {
    @GetMapping
    fun getAllCardIssuers(): ResponseEntity<List<CardIssuerDTO>> {
        return ResponseEntity<List<CardIssuerDTO>>(cardIssuerService.getAllCardIssuers(), HttpStatus.OK)
    }

    @PostMapping
    fun createCardIssuer(@RequestBody cardIssuerCreateDTO: @Valid CardIssuerCreateDTO): ResponseEntity<CardIssuerDTO> {
        return ResponseEntity<CardIssuerDTO>(cardIssuerService.createCardIssuer(cardIssuerCreateDTO), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCardIssuer(@PathVariable("id") id: Long): ResponseEntity<*> {
        cardIssuerService.deleteCardIssuer(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}