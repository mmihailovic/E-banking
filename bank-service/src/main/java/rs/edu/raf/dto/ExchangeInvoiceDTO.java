package rs.edu.raf.dto;

import java.math.BigDecimal;

public record ExchangeInvoiceDTO(Long id, BankAccountDTO senderAccount, BankAccountDTO receiverAccount, BigDecimal amount,
                                 CurrencyDTO senderCurrency, CurrencyDTO receiverCurrency, BigDecimal exchangeRate,
                                 BigDecimal profit, Long dateAndTime) {
}
