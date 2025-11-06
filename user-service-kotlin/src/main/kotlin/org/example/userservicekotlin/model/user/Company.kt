package org.example.userservicekotlin.model.user

import jakarta.persistence.Entity

@Entity
class Company(
    id: Long?,
    phoneNumber: String,
    address: String,
    val name: String,
    var accountNumbers: String?,
    val faxNumber: String,
    val TIN: Int, // tax identification number
    val registrationNumber: Int,
    val businessActivityCode: Int,
    val registryNumber: Int
): BankAccountHolder(id, phoneNumber, address)
