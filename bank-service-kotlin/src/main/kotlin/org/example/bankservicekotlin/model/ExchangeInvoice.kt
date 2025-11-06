package org.example.bankservicekotlin.model

import jakarta.persistence.*
import org.example.bankservicekotlin.model.accounts.BankAccount
import java.math.BigDecimal

@Entity
class ExchangeInvoice(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,
    @ManyToOne
    val senderAccount: BankAccount,
    @ManyToOne
    val receiverAccount: BankAccount,
    val senderAmount: BigDecimal,
    @ManyToOne
    val senderCurrency: Currency,
    @ManyToOne
    val receiverCurrency: Currency,
    val exchangeRate: BigDecimal,
    val profit: BigDecimal,
    val dateAndTime: Long
) {
}