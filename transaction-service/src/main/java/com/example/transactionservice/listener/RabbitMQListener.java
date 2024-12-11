package com.example.transactionservice.listener;

import com.example.transactionservice.model.PaymentStatus;
import com.example.transactionservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQListener {
    private PaymentService paymentService;

    @RabbitListener(queues = "${transactions.success.queue}")
    public void successTransaction(String message) {
        Long id = Long.valueOf(message);
        System.out.println("Transaction with ID: " + id + " success!");
        paymentService.updatePaymentStatus(id, PaymentStatus.SUCCESS);
    }

    @RabbitListener(queues = "${transactions.failed.queue}")
    public void failedTransaction(String message) {
        Long id = Long.valueOf(message);
        System.out.println("Transaction with ID: " + id + " failed!");
        paymentService.updatePaymentStatus(id, PaymentStatus.FAILED);
    }
}
