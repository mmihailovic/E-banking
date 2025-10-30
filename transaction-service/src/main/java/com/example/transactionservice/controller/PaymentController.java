package com.example.transactionservice.controller;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
@Tag(name = "Payment controller", description = "Manage payments")
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/internal")
    @Operation(description = "Create internal payment")
    public ResponseEntity<InternalPaymentDTO> createInternalPayment(@RequestBody @Valid InternalPaymentCreateDTO internalPaymentCreateDT0) {
        return new ResponseEntity<>(paymentService.createInternalPayment(internalPaymentCreateDT0), HttpStatus.CREATED);
    }

    @PostMapping("/external")
    @Operation(description = "Create external payment")
    public ResponseEntity<ExternalPaymentDTO> createExternalPayment(@RequestBody @Valid ExternalPaymentCreateDTO externalPaymentCreateDTO) {
        return new ResponseEntity<>(paymentService.createExternalPayment(externalPaymentCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountNumber}")
    @Operation(description = "Get all payments for bank account number")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsForBankAccountNumber(@PathVariable("accountNumber")
                                                        @Parameter(name = "Bank account number") Long accountNumber) {
        return new ResponseEntity<>(paymentService.getAllPaymentsByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get payment details by ID")
    public ResponseEntity<?> getPaymentDetailsByID(@PathVariable("id") @Parameter(name = "Payment ID") Long id) {
        return new ResponseEntity<>(paymentService.getPaymentDetails(id), HttpStatus.OK);
    }
}
