package org.example.userservicekotlin.dto

data class FavouriteRecipientDTO(
    val id: Long?,
    val clientDTO: ClientDTO,
    val recipientName: String,
    val recipientAccountNumber: String
)
