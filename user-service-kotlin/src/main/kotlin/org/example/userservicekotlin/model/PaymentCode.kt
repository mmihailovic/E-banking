package org.example.userservicekotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class PaymentCode(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val formAndBasis: String,
    val paymentDescription: String
) {
}