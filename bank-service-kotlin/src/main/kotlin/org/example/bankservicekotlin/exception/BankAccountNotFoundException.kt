package org.example.bankservicekotlin.exception

import org.springframework.http.HttpStatus

data class BankAccountNotFoundException(override val message: String): CustomException(message, HttpStatus.NOT_FOUND)
