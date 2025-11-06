package org.example.userservicekotlin.repository

import org.example.userservicekotlin.model.FavouriteRecipient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavouriteRecipientRepository : JpaRepository<FavouriteRecipient, Long> {
    fun findByClient_Id(clientId: Long): List<FavouriteRecipient>
}