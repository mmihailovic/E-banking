package org.example.stockmarketservicekotlin.mapper

import org.example.stockmarketservicekotlin.dto.ListingOwnerDTO
import org.example.stockmarketservicekotlin.model.ListingOwner
import org.springframework.stereotype.Component

@Component
class ListingOwnerMapper {
    fun listingOwnerToListingOwnerDTO(listingOwner: ListingOwner): ListingOwnerDTO {
        return ListingOwnerDTO(
            listingOwner.id, listingOwner.owner, listingOwner.ticker, listingOwner.quantity
        )
    }
}