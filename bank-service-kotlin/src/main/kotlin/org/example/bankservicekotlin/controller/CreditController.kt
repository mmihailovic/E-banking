package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.CreditDTO
import org.example.bankservicekotlin.dto.CreditRequestCreateDTO
import org.example.bankservicekotlin.dto.CreditRequestDTO
import org.example.bankservicekotlin.security.JwtUtil
import org.example.bankservicekotlin.service.CreditService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/credit")
class CreditController(
    private val creditService: CreditService, private val jwtUtil: JwtUtil
) {
    @PostMapping("/apply")
    fun applyForCredit(@RequestBody creditRequestCreateDto: @Valid CreditRequestCreateDTO): ResponseEntity<CreditRequestDTO> {
        return ResponseEntity<CreditRequestDTO>(creditService.applyForCredit(creditRequestCreateDto), HttpStatus.OK)
    }

    @PutMapping("/deny/{id}")
    fun denyCreditRequest(@PathVariable("id") id: Long): ResponseEntity<CreditRequestDTO> {
        return ResponseEntity<CreditRequestDTO>(creditService.dennyCreditRequest(id), HttpStatus.OK)
    }

    @PutMapping("/approve/{id}")
    fun approveCredit(@PathVariable("id") id: Long): ResponseEntity<CreditRequestDTO> {
        return ResponseEntity<CreditRequestDTO>(creditService.approveCreditRequest(id), HttpStatus.OK)
    }

    @GetMapping("/requests/all/{status}")
    fun getAllCreditRequestsWithStatus(@PathVariable("status") status: String): ResponseEntity<List<CreditRequestDTO>> {
        return ResponseEntity<List<CreditRequestDTO>>(
            creditService.getAllCreditRequestsWithStatus(status),
            HttpStatus.OK
        )
    }

    @GetMapping("/requests/{status}")
    fun getAllCreditRequestsForUserWithStatus(@PathVariable("status") status: String): ResponseEntity<List<CreditRequestDTO>> {
        return ResponseEntity<List<CreditRequestDTO>>(
            creditService.getAllCreditRequestForUserWithStatus(
                jwtUtil.getIDForLoggedUser(),
                status
            ), HttpStatus.OK
        )
    }

    @GetMapping("/all")
    fun getAllCredits(): ResponseEntity<List<CreditDTO>> {
        return ResponseEntity<List<CreditDTO>>(creditService.getAllCredits(), HttpStatus.OK)
    }

    @GetMapping
    fun getAllCreditsForUser(): ResponseEntity<List<CreditDTO>> {
        return ResponseEntity<List<CreditDTO>>(creditService.getAllCredits(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getCreditDetails(@PathVariable("id") id: Long): ResponseEntity<CreditDTO> {
        return ResponseEntity<CreditDTO>(creditService.getDetailedCredit(id), HttpStatus.OK)
    }
}