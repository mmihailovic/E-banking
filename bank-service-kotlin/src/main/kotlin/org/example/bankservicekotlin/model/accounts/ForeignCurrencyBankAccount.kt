package org.example.bankservicekotlin.model.accounts

import jakarta.persistence.Entity
import org.example.bankservicekotlin.model.Currency
import java.math.BigDecimal

@Entity
class ForeignCurrencyBankAccount(
    id: Long?,
    accountNumber: Long?,
    owner: Long?,
    balance: BigDecimal,
    availableBalance: BigDecimal,
    creator: Long, // employee who created account
    creationDate: Long,
    currency: Currency,
    active: Boolean,
    val maintenancePrice: BigDecimal
) : BankAccount(id, accountNumber, owner, balance, availableBalance, creator, creationDate, currency, active) {
}