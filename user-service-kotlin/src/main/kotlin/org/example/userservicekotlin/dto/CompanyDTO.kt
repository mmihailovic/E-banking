package org.example.userservicekotlin.dto

data class CompanyDTO(
    val id: Long?,
    val phoneNumber: String,
    val address: String,
    val name: String,
    val accountNumbers: String?,
    val faxNumber: String,
    val TIN: Int,
    val registrationNumber: Int,
    val businessActivityCode: Int,
    val registryNumber: Int
)
