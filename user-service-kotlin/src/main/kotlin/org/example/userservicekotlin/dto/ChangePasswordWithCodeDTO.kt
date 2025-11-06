package org.example.userservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class ChangePasswordWithCodeDTO(
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
    val email: String,
    @NotNull val password: String,
    @NotNull val code: String
)
