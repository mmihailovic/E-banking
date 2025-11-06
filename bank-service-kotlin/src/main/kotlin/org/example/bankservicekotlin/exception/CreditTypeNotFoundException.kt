package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CreditTypeNotFoundException(val id: Long) :
    CustomException("Credit type with ID $id not found!", HttpStatus.NOT_FOUND)
