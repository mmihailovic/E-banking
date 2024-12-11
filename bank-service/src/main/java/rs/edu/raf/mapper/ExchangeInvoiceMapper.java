package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.CurrencyDTO;
import rs.edu.raf.dto.ExchangeInvoiceDTO;
import rs.edu.raf.dto.BankAccountDTO;
import rs.edu.raf.model.ExchangeInvoice;

@Component
@AllArgsConstructor
public class ExchangeInvoiceMapper {
    private BankAccountMapper bankAccountMapper;
    private CurrencyMapper currencyMapper;

    public ExchangeInvoiceDTO exchangeInvoiceToExchangeInvoiceDTO(ExchangeInvoice exchangeInvoice) {
        BankAccountDTO senderAccount = bankAccountMapper.bankAccountToBankAccountDTO(exchangeInvoice.getSenderAccount());
        BankAccountDTO receiverAccount = bankAccountMapper.bankAccountToBankAccountDTO(exchangeInvoice.getReceiverAccount());
        CurrencyDTO senderCurrency = currencyMapper.currencyToCurrencyDTO(exchangeInvoice.getSenderCurrency());
        CurrencyDTO receiverCurrency = currencyMapper.currencyToCurrencyDTO(exchangeInvoice.getReceiverCurrency());

        return new ExchangeInvoiceDTO(exchangeInvoice.getId(), senderAccount, receiverAccount,
                exchangeInvoice.getSenderAmount(), senderCurrency, receiverCurrency, exchangeInvoice.getExchangeRate(),
                exchangeInvoice.getProfit(), exchangeInvoice.getDateAndTime());
    }
}
