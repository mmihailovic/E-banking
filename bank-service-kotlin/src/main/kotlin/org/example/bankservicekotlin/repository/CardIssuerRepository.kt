package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.card.CardIssuer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardIssuerRepository : JpaRepository<CardIssuer, Long> {
}