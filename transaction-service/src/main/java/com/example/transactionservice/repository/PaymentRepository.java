package com.example.transactionservice.repository;

import com.example.transactionservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllBySenderAccountNumber(Long senderAccountNumber);
}
