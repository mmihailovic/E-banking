package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.CardDTO
import org.example.bankservicekotlin.dto.CreateCardDTO
import org.example.bankservicekotlin.security.JwtUtil
import org.example.bankservicekotlin.service.CardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/cards")
class CardController(
    private val cardService: CardService,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/create")
    fun createCard(@RequestBody createCardDto: @Valid CreateCardDTO): ResponseEntity<CardDTO> {
        return ResponseEntity<CardDTO>(cardService.createCard(createCardDto), HttpStatus.CREATED)
    }

    @PatchMapping("/block/{cardNumber}")
    fun blockCard(@PathVariable("cardNumber") cardNumber: String): ResponseEntity<*> {
        cardService.blockCard(cardNumber)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PatchMapping("/unblock/{cardNumber}")
    fun unblockCard(@PathVariable("cardNumber") cardNumber: String): ResponseEntity<*> {
        cardService.unblockCard(cardNumber)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @PatchMapping("/deactivate/{cardNumber}")
    fun deactivateCard(@PathVariable("cardNumber") cardNumber: String): ResponseEntity<*> {
        cardService.deactivateCard(cardNumber)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping
    fun getAllCards(): ResponseEntity<List<CardDTO>> {
        return ResponseEntity<List<CardDTO>>(cardService.getAllCards(), HttpStatus.OK)
    }

    @GetMapping("/client/{id}")
    fun getAllCardsForClient(@PathVariable("id") id: Long): ResponseEntity<List<CardDTO>> {
        return ResponseEntity<List<CardDTO>>(cardService.getAllCardsForUser(id), HttpStatus.OK)
    }

    @GetMapping("/client")
    fun getAllCardsForLoggedClient(): ResponseEntity<List<CardDTO>> {
        return ResponseEntity<List<CardDTO>>(cardService.getAllCardsForUser(jwtUtil.getIDForLoggedUser()), HttpStatus.OK)
    }
}