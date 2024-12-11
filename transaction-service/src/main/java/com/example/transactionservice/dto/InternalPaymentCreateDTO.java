package com.example.transactionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record InternalPaymentCreateDTO(@NotNull Long senderAccountNumber,
                                       @NotNull Long receiverAccountNumber,
                                       @NotNull @Positive BigDecimal amount) {
}
