package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class CreditNotFoundException(val id: Long) :
    CustomException("Credit with ID $id not found!", HttpStatus.NOT_FOUND)
