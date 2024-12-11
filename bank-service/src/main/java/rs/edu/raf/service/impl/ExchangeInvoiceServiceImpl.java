package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.annotations.GeneratedCrudOperation;
import rs.edu.raf.dto.ExchangeInvoiceDTO;
import rs.edu.raf.mapper.ExchangeInvoiceMapper;
import rs.edu.raf.model.ExchangeInvoice;
import rs.edu.raf.model.accounts.BankAccount;
import rs.edu.raf.repository.ExchangeInvoiceRepository;
import rs.edu.raf.service.ExchangeInvoiceService;
import rs.edu.raf.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ExchangeInvoiceServiceImpl implements ExchangeInvoiceService {
    private ExchangeInvoiceRepository exchangeInvoiceRepository;
    private ExchangeInvoiceMapper exchangeInvoiceMapper;
    private ExchangeRateService exchangeRateService;

    @Override
    public void createInvoiceForExchange(BankAccount sender, BankAccount receiver, BigDecimal amount) {
        ExchangeInvoice exchangeInvoice = new ExchangeInvoice();
        exchangeInvoice.setSenderAccount(sender);
        exchangeInvoice.setReceiverAccount(receiver);
        exchangeInvoice.setSenderAmount(amount);
        exchangeInvoice.setSenderCurrency(sender.getCurrency());
        exchangeInvoice.setReceiverCurrency(receiver.getCurrency());
        exchangeInvoice.setExchangeRate(exchangeRateService.exchangeRate(sender.getCurrency().getCode(), receiver.getCurrency().getCode()));
        exchangeInvoice.setProfit(amount.multiply(new BigDecimal("0.005")));
        exchangeInvoice.setDateAndTime(System.currentTimeMillis());
        exchangeInvoiceRepository.save(exchangeInvoice);
    }

    @GeneratedCrudOperation
    @Override
    public List<ExchangeInvoiceDTO> listInvoicesByCurrency(String currency) {
        return exchangeInvoiceRepository.findAllBySenderCurrency_Code(currency)
                .stream()
                .map(exchangeInvoiceMapper::exchangeInvoiceToExchangeInvoiceDTO)
                .toList();
    }
}
