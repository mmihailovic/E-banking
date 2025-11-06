package org.example.stockmarketservicekotlin.repository

import org.example.stockmarketservicekotlin.model.ListingOwner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ListingOwnerRepository :JpaRepository<ListingOwner, Long>{
    fun findByOwnerAndTicker(owner: Long, ticker: String): Optional<ListingOwner>
}