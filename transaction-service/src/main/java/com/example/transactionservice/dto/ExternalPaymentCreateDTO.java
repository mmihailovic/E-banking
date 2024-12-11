package com.example.transactionservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ExternalPaymentCreateDTO (
        @NotNull Long senderAccountNumber,
        @NotNull Long receiverAccountNumber,
        @Positive BigDecimal amount,
        @NotNull @NotEmpty String recipientName,
        @NotNull Integer paymentReferenceNumber,
        @NotNull Integer paymentCode,
        @NotNull String paymentPurpose,
        @NotNull String code
) {
}
