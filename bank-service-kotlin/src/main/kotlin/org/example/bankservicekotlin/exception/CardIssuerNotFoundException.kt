package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CardIssuerNotFoundException(val id: Long) :
    CustomException("Card issuer with $id not found!", HttpStatus.NOT_FOUND)
