package com.example.transactionservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderAccountNumber;
    private Long receiverAccountNumber;
    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Long transactionCreationTime;
    private Long transactionExecutionTime;
}
