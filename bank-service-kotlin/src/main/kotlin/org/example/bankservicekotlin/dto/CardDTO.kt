package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class CardDTO(
    val id: Long?,
    val number: String,
    val type: String,
    val cardIssuer: CardIssuerDTO,
    val name: String,
    val creationDate: Long,
    val expirationDate: Long,
    val bankAccount: BankAccountDTO,
    val cvv: Int,
    val cardLimit: BigDecimal,
    val status: String
)
