package org.example.userservicekotlin.dto

import jakarta.validation.constraints.Pattern

data class ClientRegisterDTO(
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
    val email: String,
    @Pattern(
        regexp = "^(\\+381|0)6\\d{7,8}$",
        message = "The phone number must start with +381 or 0, followed by 6 and then 7 or 8 digits!"
    )
    val phoneNumber: String,
    val bankAccountNumber: String,
    val password: String,
    val code: String
)
