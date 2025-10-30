package com.example.transactionservice.service;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.model.PaymentStatus;

import java.util.List;

public interface PaymentService {
    /**
     * Creates internal payment
     * @param internalPaymentCreateDTO DTO object contains information about internal payment
     * @return {@link InternalPaymentDTO} object representing created internal payment
     */
    InternalPaymentDTO createInternalPayment(InternalPaymentCreateDTO internalPaymentCreateDTO);

    /**
     * Creates external payment
     * @param externalPaymentCreateDTO DTO object contains information about external payment
     * @return {@link ExternalPaymentDTO} object representing created external payment
     */
    ExternalPaymentDTO createExternalPayment(ExternalPaymentCreateDTO externalPaymentCreateDTO);

    /**
     * Get all payments for specified bank account number
     * @param accountNumber the bank account number
     * @return List of {@link PaymentDTO} objects represents payments
     */
    List<PaymentDTO> getAllPaymentsByAccountNumber(Long accountNumber);

    /**
     * Get payment details
     * @param id ID of the payment
     * @return DTO object contains payment details
     */
    Object getPaymentDetails(Long id);

    /**
     * Update payment status
     * @param id ID of the payment
     * @param paymentStatus new payment status
     */
    void updatePaymentStatus(Long id, PaymentStatus paymentStatus);
}
