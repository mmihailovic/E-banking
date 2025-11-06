package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class PaymentQueueDTO(
    val id: Long,
    val email: String,
    val senderAccountNumber: Long,
    val receiverAccountNumber: Long,
    val amount: BigDecimal,
    val userId: Long,
    val type: String,
    val paymentCode: String
)
