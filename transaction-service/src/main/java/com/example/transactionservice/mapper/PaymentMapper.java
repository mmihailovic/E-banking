package com.example.transactionservice.mapper;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.model.ExternalPayment;
import com.example.transactionservice.model.InternalPayment;
import com.example.transactionservice.model.Payment;
import com.example.transactionservice.model.PaymentStatus;
import com.example.transactionservice.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentMapper {
    private JwtUtil jwtUtil;

    public ExternalPayment externalPaymentCreateDTOtoExternalPayment(ExternalPaymentCreateDTO externalPaymentCreateDTO) {
        ExternalPayment externalPayment = new ExternalPayment();

        externalPayment.setPaymentCode(externalPaymentCreateDTO.paymentCode());
        externalPayment.setPaymentPurpose(externalPaymentCreateDTO.paymentPurpose());
        externalPayment.setPaymentReferenceNumber(externalPaymentCreateDTO.paymentReferenceNumber());
        externalPayment.setRecipientName(externalPaymentCreateDTO.recipientName());
        externalPayment.setSenderAccountNumber(externalPaymentCreateDTO.senderAccountNumber());
        externalPayment.setReceiverAccountNumber(externalPaymentCreateDTO.receiverAccountNumber());
        externalPayment.setAmount(externalPaymentCreateDTO.amount());
        externalPayment.setPaymentStatus(PaymentStatus.PENDING);
        externalPayment.setTransactionCreationTime(System.currentTimeMillis());

        return externalPayment;
    }

    public InternalPayment internalPaymentCreateDTOtoInternalPayment(InternalPaymentCreateDTO internalPaymentCreateDTO) {
        InternalPayment internalPayment = new InternalPayment();

        internalPayment.setSenderAccountNumber(internalPaymentCreateDTO.senderAccountNumber());
        internalPayment.setReceiverAccountNumber(internalPaymentCreateDTO.receiverAccountNumber());
        internalPayment.setAmount(internalPaymentCreateDTO.amount());
        internalPayment.setPaymentStatus(PaymentStatus.PENDING);
        internalPayment.setTransactionCreationTime(System.currentTimeMillis());

        return internalPayment;
    }

    public InternalPaymentDTO internalPaymentToInternalPaymentDTO(InternalPayment internalPayment) {
        return new InternalPaymentDTO(internalPayment.getId(), internalPayment.getSenderAccountNumber(),
                internalPayment.getReceiverAccountNumber(), internalPayment.getAmount(),
                internalPayment.getPaymentStatus().toString(), internalPayment.getTransactionCreationTime(),
                internalPayment.getTransactionExecutionTime());
    }

    public ExternalPaymentDTO externalPaymentToExternalPaymentDTO(ExternalPayment externalPayment) {
        return new ExternalPaymentDTO(externalPayment.getId(), externalPayment.getSenderAccountNumber(),
                externalPayment.getReceiverAccountNumber(), externalPayment.getAmount(),
                externalPayment.getPaymentStatus().toString(), externalPayment.getTransactionCreationTime(),
                externalPayment.getTransactionExecutionTime(), externalPayment.getRecipientName(),
                externalPayment.getPaymentReferenceNumber(), externalPayment.getPaymentCode(), externalPayment.getPaymentPurpose());
    }

    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getSenderAccountNumber(), payment.getReceiverAccountNumber(),
                payment.getAmount(), payment.getPaymentStatus().toString(), payment.getTransactionCreationTime(),
                payment.getTransactionExecutionTime());
    }

    public PaymentBrokerDTO paymentToPaymentBrokerDto(Payment payment, String type, String code) {
        String email = jwtUtil.extractUsername(jwtUtil.extractToken());
        return new PaymentBrokerDTO(payment.getId(), email, payment.getSenderAccountNumber(), payment.getReceiverAccountNumber()
                , payment.getAmount(), jwtUtil.getIDForLoggedUser(), type, code);
    }
}
