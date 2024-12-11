package com.example.transactionservice.service.impl;

import com.example.transactionservice.dto.*;
import com.example.transactionservice.mapper.PaymentMapper;
import com.example.transactionservice.model.ExternalPayment;
import com.example.transactionservice.model.InternalPayment;
import com.example.transactionservice.model.Payment;
import com.example.transactionservice.model.PaymentStatus;
import com.example.transactionservice.repository.PaymentRepository;
import com.example.transactionservice.service.PaymentService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {
    @Value("${transactions.required.routing.key}")
    private String TRANSACTION_ROUTING_KEY;
    @Value("${transactions.required.exchange}")
    private String TRANSACTION_EXCHANGE;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final KafkaTemplate<String, Payment> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate userServiceRestTemplate;
    @Autowired
    private Gson gson;

    @Override
    public InternalPaymentDTO createInternalPayment(InternalPaymentCreateDTO internalPaymentCreateDTO, Long userId) {
        InternalPayment internalPayment = paymentRepository.save(paymentMapper.internalPaymentCreateDTOtoInternalPayment(internalPaymentCreateDTO));
        PaymentBrokerDTO paymentBrokerDTO = paymentMapper.paymentToPaymentBrokerDto(internalPayment, userId,
                "INTERNAL", null);

        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_ROUTING_KEY, gson.toJson(paymentBrokerDTO));
        return paymentMapper.internalPaymentToInternalPaymentDTO(internalPayment);
    }

    @Override
    public ExternalPaymentDTO createExternalPayment(ExternalPaymentCreateDTO externalPaymentCreateDTO, Long userId) {
        ExternalPayment externalPayment = paymentRepository.save(paymentMapper.externalPaymentCreateDTOtoExternalPayment(externalPaymentCreateDTO));

        PaymentBrokerDTO paymentBrokerDTO = paymentMapper.paymentToPaymentBrokerDto(externalPayment, userId,
                "EXTERNAL", externalPaymentCreateDTO.code());

        Boolean validCode = userServiceRestTemplate.exchange("/user/" + paymentBrokerDTO.email() +
                        "/code/"+paymentBrokerDTO.paymentCode(),
                HttpMethod.GET, null, Boolean.class).getBody();

        if(validCode == null || !validCode) {
            throw new RuntimeException();
        }

        rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_ROUTING_KEY, gson.toJson(paymentBrokerDTO));
        return paymentMapper.externalPaymentToExternalPaymentDTO(externalPayment);
    }

    @Override
    public List<PaymentDTO> getAllPaymentsByAccountNumber(Long accountNumber) {
        return paymentRepository.findAllBySenderAccountNumber(accountNumber).stream().map(paymentMapper::paymentToPaymentDTO).toList();
    }

    @Override
    public Object getPaymentDetails(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if(optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            if(payment instanceof InternalPayment) {
                return paymentMapper.internalPaymentToInternalPaymentDTO((InternalPayment) payment);
            }
            if(payment instanceof ExternalPayment) {
                return paymentMapper.externalPaymentToExternalPaymentDTO((ExternalPayment) payment);
            }
        }

        throw new RuntimeException();
    }

    @Override
    public void updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);

        if(optionalPayment.isEmpty()) {
            throw new RuntimeException();
        }

        Payment payment = optionalPayment.get();
        payment.setPaymentStatus(paymentStatus);
        payment.setTransactionExecutionTime(System.currentTimeMillis());
        paymentRepository.save(payment);
    }
}
