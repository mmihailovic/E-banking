package com.example.transactionservice.dto;

import java.math.BigDecimal;

public record PaymentBrokerDTO(Long id,
                               String email,
                               Long senderAccountNumber,
                               Long receiverAccountNumber,
                               BigDecimal amount,
                               Long userId,
                               String type,
                               String paymentCode) {
}
