package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CountryNotFoundException(val id: Long) :
    CustomException("Country with ID $id not found!", HttpStatus.NOT_FOUND)
