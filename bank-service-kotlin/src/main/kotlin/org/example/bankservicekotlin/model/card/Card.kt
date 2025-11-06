package org.example.bankservicekotlin.model.card

import jakarta.persistence.*
import org.example.bankservicekotlin.model.accounts.BankAccount
import java.math.BigDecimal

@Entity
class Card(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var number: String,
    @Enumerated(value = EnumType.STRING)
    val type: CardType,
    @ManyToOne
    val cardIssuer: CardIssuer,
    val name: String,
    val creationDate: Long,
    val expirationDate: Long,
    @ManyToOne
    val bankAccount: BankAccount,
    val cvv: Int,
    val cardLimit: BigDecimal,
    @Enumerated(value = EnumType.STRING)
    var cardStatus: CardStatus
) {
}