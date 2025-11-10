package org.example.bankservicekotlin.model.accounts

import jakarta.persistence.*
import org.example.bankservicekotlin.model.Currency
import java.math.BigDecimal

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class BankAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(unique = true)
    var accountNumber: Long?,
    var owner: Long?,
    var balance: BigDecimal,
    var availableBalance: BigDecimal,
    val creator: Long, // employee who created account
    val creationDate: Long,
    @ManyToOne
    val currency: Currency,
    var active: Boolean
) {
}