package org.example.userservicekotlin.dto

import jakarta.validation.constraints.NotNull

data class CompanyCreateDTO(
    @NotNull val name: String,
    @NotNull val phoneNumber: String,
    @NotNull val address: String,
    @NotNull val faxNumber: String,
    @NotNull val TIN: Int,
    @NotNull val registrationNumber: Int,
    @NotNull val businessActivityCode: Int,
    @NotNull val registryNumber: Int
)
