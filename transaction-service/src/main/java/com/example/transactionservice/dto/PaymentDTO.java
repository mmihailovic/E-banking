package com.example.transactionservice.dto;

import java.math.BigDecimal;

public record PaymentDTO(Long id, Long senderAccountNumber, Long receiverAccountNumber, BigDecimal amount,
                         String paymentStatus, Long transactionCreationTime, Long transactionExecutionTime) {
}
