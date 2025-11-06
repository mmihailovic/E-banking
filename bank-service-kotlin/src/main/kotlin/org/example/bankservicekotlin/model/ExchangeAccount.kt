package org.example.bankservicekotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.math.BigDecimal

@Entity
class ExchangeAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var balance: BigDecimal,
    val currencyCode: String) {
}