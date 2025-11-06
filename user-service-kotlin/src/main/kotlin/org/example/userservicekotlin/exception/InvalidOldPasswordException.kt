package org.example.userservicekotlin.exception

import org.springframework.http.HttpStatus

data class InvalidOldPasswordException(override val message: String = "Current password isn't correct!") :
    CustomException(message, HttpStatus.BAD_REQUEST)
