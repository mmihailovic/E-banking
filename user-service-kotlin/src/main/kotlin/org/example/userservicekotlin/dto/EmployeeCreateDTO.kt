package org.example.userservicekotlin.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class EmployeeCreateDTO(
    val companyId: Long,
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only one or more letters are allowed!")
    val firstName: String,
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only one or more letters are allowed!")
    val lastName: String,
    @NotNull val JMBG: String,
    @NotNull val dateOfBirth: Long,
    @Pattern(regexp = "^[M|F]$", message = "Gender can be M or F!")
    val gender: String,
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email address must be valid!")
    val email: String,
    @Pattern(
        regexp = "^(\\+381|0)6\\d{7,8}$",
        message = "The phone number must start with +381 or 0, followed by 6 and then 7 or 8 digits!"
    )
    val phoneNumber: String,
    val address: String,
    val password: String,
    @NotNull
    val position: String,
    @NotNull
    val department: String,
    val roles: List<String>,
    val active: Boolean
)
