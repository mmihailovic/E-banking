package org.example.bankservicekotlin.dto

data class CurrencyDTO(
    val id: Long?,
    val name: String,
    val code: String,
    val symbol: String,
    val countryDTO: CountryDTO
)
