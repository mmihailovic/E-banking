package org.example.stockmarketservicekotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class ListingOwner(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val owner: Long,
    val ticker: String,
    var quantity: Int
) {}