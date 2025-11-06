package org.example.userservicekotlin.mapper

import org.example.userservicekotlin.dto.FavouriteRecipientCreateDTO
import org.example.userservicekotlin.dto.FavouriteRecipientDTO
import org.example.userservicekotlin.model.FavouriteRecipient
import org.example.userservicekotlin.model.user.Client
import org.springframework.stereotype.Component

@Component
class FavouriteRecipientMapper(val clientMapper: ClientMapper) {
    fun favouriteRecipientToFavouriteRecipientDTO(source: FavouriteRecipient): FavouriteRecipientDTO {
        return FavouriteRecipientDTO(
            source.id,
            clientMapper.clientToClientDTO(source.client),
            source.recipientName,
            source.recipientAccountNumber
        )
    }

    fun favouriteRecipientRequestDTOtoFavouriteRecipient(
        source: FavouriteRecipientCreateDTO,
        client: Client
    ): FavouriteRecipient {
        return FavouriteRecipient(null, client, source.recipientName, source.recipientAccountNumber)
    }
}