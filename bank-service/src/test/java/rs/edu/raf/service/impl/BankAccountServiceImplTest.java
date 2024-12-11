package rs.edu.raf.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import rs.edu.raf.dto.PaymentQueueDTO;
import rs.edu.raf.model.Currency;
import rs.edu.raf.model.accounts.BankAccount;
import rs.edu.raf.repository.accounts.BankAccountRepository;
import rs.edu.raf.service.ExchangeAccountService;
import rs.edu.raf.service.ExchangeInvoiceService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ExchangeAccountService exchangeAccountService;

    @Mock
    private ExchangeInvoiceService exchangeInvoiceService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccount sender;

    @Mock
    private BankAccount receiver;
    @Mock
    private Currency currency;
    @Mock
    private Currency foreignCurrency;
    @Mock
    private PaymentQueueDTO paymentQueueDTO;

    @Test
    void processPayment_Success() {
        when(paymentQueueDTO.id()).thenReturn(1L);
        when(paymentQueueDTO.senderAccountNumber()).thenReturn(123L);
        when(paymentQueueDTO.receiverAccountNumber()).thenReturn(456L);
        when(paymentQueueDTO.amount()).thenReturn(new BigDecimal("100.00"));
        when(paymentQueueDTO.type()).thenReturn("INTERNAL");
        when(paymentQueueDTO.userId()).thenReturn(1L);

        when(bankAccountRepository.findByAccountNumber(123L)).thenReturn(Optional.of(sender));
        when(bankAccountRepository.findByAccountNumber(456L)).thenReturn(Optional.of(receiver));

        when(sender.getActive()).thenReturn(true);
        when(receiver.getActive()).thenReturn(true);
        when(sender.getAvailableBalance()).thenReturn(new BigDecimal("300.00"));
        when(sender.getOwner()).thenReturn(1L);
        when(receiver.getOwner()).thenReturn(1L);
        when(sender.getBalance()).thenReturn(new BigDecimal("300.0"));
        when(receiver.getBalance()).thenReturn(new BigDecimal("200.0"));
        when(sender.getAvailableBalance()).thenReturn(new BigDecimal("300.0"));
        when(receiver.getAvailableBalance()).thenReturn(new BigDecimal("200.0"));
        when(sender.getCurrency()).thenReturn(currency);
        when(receiver.getCurrency()).thenReturn(currency);
        when(currency.getId()).thenReturn(1L);
        when(currency.getId()).thenReturn(1L);

        bankAccountService.processPayment(paymentQueueDTO);

        verify(bankAccountRepository).save(sender);
        verify(bankAccountRepository).save(receiver);
    }

    @Test
    void processPayment_SenderOrReceiverNotFound() {
        when(paymentQueueDTO.id()).thenReturn(1L);
        when(paymentQueueDTO.senderAccountNumber()).thenReturn(123L);
        when(paymentQueueDTO.receiverAccountNumber()).thenReturn(999L);

        when(bankAccountRepository.findByAccountNumber(123L)).thenReturn(Optional.of(sender));
        when(bankAccountRepository.findByAccountNumber(999L)).thenReturn(Optional.empty());

        bankAccountService.processPayment(paymentQueueDTO);

        verify(sender, never()).setBalance(any());
        verify(receiver, never()).setBalance(any());
    }

    @Test
    void processPayment_DifferentCurrencies() throws Exception {
        when(paymentQueueDTO.id()).thenReturn(1L);
        when(paymentQueueDTO.senderAccountNumber()).thenReturn(123L);
        when(paymentQueueDTO.receiverAccountNumber()).thenReturn(456L);
        when(paymentQueueDTO.amount()).thenReturn(new BigDecimal("100.0"));

        when(bankAccountRepository.findByAccountNumber(123L)).thenReturn(Optional.of(sender));
        when(bankAccountRepository.findByAccountNumber(456L)).thenReturn(Optional.of(receiver));

        when(sender.getActive()).thenReturn(true);
        when(receiver.getActive()).thenReturn(true);
        when(receiver.getBalance()).thenReturn(new BigDecimal("200.0"));
        when(sender.getAvailableBalance()).thenReturn(new BigDecimal("300.00"));
        when(receiver.getAvailableBalance()).thenReturn(new BigDecimal("200.0"));
        when(currency.getCode()).thenReturn("RSD");
        when(foreignCurrency.getCode()).thenReturn("EUR");
        when(currency.getId()).thenReturn(1L);
        when(foreignCurrency.getId()).thenReturn(2L);
        when(sender.getCurrency()).thenReturn(currency);
        when(receiver.getCurrency()).thenReturn(foreignCurrency);
        when(paymentQueueDTO.type()).thenReturn("INTERNAL");
        when(sender.getBalance()).thenReturn(new BigDecimal("300.0"));

        when(exchangeAccountService.exchange("RSD", "EUR", new BigDecimal("1.00"))).thenThrow(new RuntimeException("Exchange failed"));

        bankAccountService.processPayment(paymentQueueDTO);

        verify(exchangeAccountService).exchange("RSD","EUR",new BigDecimal("100.0"));
    }

}