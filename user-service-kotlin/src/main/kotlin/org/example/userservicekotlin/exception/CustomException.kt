package org.example.userservicekotlin.exception

import org.springframework.http.HttpStatus

open class CustomException(message: String, val httpStatus: HttpStatus):RuntimeException(message) {
}