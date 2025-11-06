package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CreditRequestNotFoundException(val id: Long) :
    CustomException("Credit request with ID $id not found!", HttpStatus.NOT_FOUND)
