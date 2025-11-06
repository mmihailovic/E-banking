package org.example.userservicekotlin.dto

import jakarta.validation.constraints.Pattern

data class LoginDTO(
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
    val username: String,
    val password: String
)
