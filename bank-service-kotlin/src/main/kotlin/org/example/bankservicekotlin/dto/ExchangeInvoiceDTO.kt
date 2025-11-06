package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class ExchangeInvoiceDTO(
    val id: Long?,
    val senderAccount: BankAccountDTO,
    val receiverAccount: BankAccountDTO,
    val amount: BigDecimal,
    val senderCurrency: CurrencyDTO,
    val receiverCurrency: CurrencyDTO,
    val exchangeRate: BigDecimal,
    val profit: BigDecimal,
    val dateAndTime: Long
)
