package org.example.userservicekotlin.dto

import jakarta.validation.constraints.NotNull

data class ChangePasswordDTO(
    @NotNull val oldPassword: String,
    @NotNull val newPassword: String
)