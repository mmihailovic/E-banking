package org.example.stockmarketservicekotlin.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.function.Consumer

@ControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(value = [CustomException::class])
    fun handleRuntimeException(e: CustomException): ResponseEntity<Any?> {
        return ResponseEntity<Any?>(e.message, e.httpStatus)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleRuntimeException(e: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors: MutableMap<String, String?> = HashMap()
        e.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            errors[(error as FieldError).field] = error.getDefaultMessage()
        })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}