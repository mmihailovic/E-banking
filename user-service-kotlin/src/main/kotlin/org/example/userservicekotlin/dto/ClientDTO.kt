package org.example.userservicekotlin.dto

data class ClientDTO(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val JMBG: String,
    val dateOfBirth: Long,
    val gender: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val bankAccounts: String?
)
