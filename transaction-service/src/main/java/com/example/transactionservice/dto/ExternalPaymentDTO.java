package com.example.transactionservice.dto;

import java.math.BigDecimal;

public record ExternalPaymentDTO(Long id, Long senderAccountNumber, Long receiverAccountNumber, BigDecimal amount,
                                 String paymentStatus, Long transactionCreationTime, Long transactionExecutionTime,
                                 String recipientName, Integer paymentReferenceNumber, Integer paymentCode,
                                 String paymentPurpose) {
}
