package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.CountryCreateDTO
import org.example.bankservicekotlin.dto.CountryDTO
import org.example.bankservicekotlin.service.CountryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/country")
class CountryController(private val countryService: CountryService) {
    @GetMapping
    fun getAllCountries(): ResponseEntity<List<CountryDTO>> {
        return ResponseEntity<List<CountryDTO>>(countryService.getAllCountries(), HttpStatus.OK)
    }

    @PostMapping
    fun createCurrency(@RequestBody countryCreateDTO: @Valid CountryCreateDTO): ResponseEntity<CountryDTO> {
        return ResponseEntity<CountryDTO>(countryService.createCountry(countryCreateDTO), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteCountry(@PathVariable("id") id: Long): ResponseEntity<*> {
        countryService.deleteCountry(id)
        return ResponseEntity<Any>(HttpStatus.OK)
    }

}