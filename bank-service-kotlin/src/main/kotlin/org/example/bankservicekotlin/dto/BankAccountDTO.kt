package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class BankAccountDTO(
    val id: Long?,
    val accountNumber: String,
    val owner: Long?,
    val balance: BigDecimal,
    val availableBalance: BigDecimal,
    val creator: Long,
    val creationDate: Long,
    val currencyDTO: CurrencyDTO,
    val active: Boolean
)
