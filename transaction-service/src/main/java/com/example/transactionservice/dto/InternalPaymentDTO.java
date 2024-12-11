package com.example.transactionservice.dto;

import java.math.BigDecimal;

public record InternalPaymentDTO(Long id, Long senderAccountNumber, Long receiverAccountNumber, BigDecimal amount,
                                 String paymentStatus, Long transactionCreationTime, Long transactionExecutionTime) {
}
