package com.example.transactionservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExternalPayment extends Payment{
    private String recipientName;
    private Integer paymentReferenceNumber;
    private Integer paymentCode;
    private String paymentPurpose;
}
