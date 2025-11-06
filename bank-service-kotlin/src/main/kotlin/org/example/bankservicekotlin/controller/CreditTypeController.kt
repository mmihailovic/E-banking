package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.CreditTypeCreateDTO
import org.example.bankservicekotlin.dto.CreditTypeDTO
import org.example.bankservicekotlin.service.CreditTypeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/credit-type")
class CreditTypeController(private val creditTypeService: CreditTypeService) {
    @GetMapping
    fun getAllCreditTypes(): ResponseEntity<List<CreditTypeDTO>> {
        return ResponseEntity<List<CreditTypeDTO>>(creditTypeService.getAllCreditTypes(), HttpStatus.OK)
    }

    @PostMapping
    fun createCreditType(@RequestBody creditTypeCreateDTO: @Valid CreditTypeCreateDTO): ResponseEntity<CreditTypeDTO> {
        return ResponseEntity<CreditTypeDTO>(creditTypeService.createCreditType(creditTypeCreateDTO), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCreditType(@PathVariable("id") id: Long): ResponseEntity<*> {
        creditTypeService.deleteCreditType(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

}