package org.example.userservicekotlin.exception

import org.springframework.http.HttpStatus

data class InvalidTokenException(override val message: String): CustomException(message, HttpStatus.UNPROCESSABLE_ENTITY)
