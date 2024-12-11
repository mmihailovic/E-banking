package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.edu.raf.exceptions.ExchangeAccountNotFoundException;
import rs.edu.raf.model.ExchangeAccount;
import rs.edu.raf.repository.ExchangeAccountRepository;
import rs.edu.raf.service.ExchangeAccountService;
import rs.edu.raf.service.ExchangeRateService;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ExchangeAccountServiceImpl implements ExchangeAccountService {
    private ExchangeAccountRepository exchangeAccountRepository;
    private ExchangeRateService exchangeRateService;


    @Override
    public BigDecimal exchange(String fromCurrency, String toCurrency, BigDecimal amount) throws Exception {
        ExchangeAccount senderExchangeAccount = exchangeAccountRepository.findByCurrencyCode(fromCurrency)
                .orElseThrow(()->new ExchangeAccountNotFoundException(fromCurrency));

        ExchangeAccount receiverExchangeAccount = exchangeAccountRepository.findByCurrencyCode(toCurrency)
                .orElseThrow(()->new ExchangeAccountNotFoundException(toCurrency));

        BigDecimal amountToReceive = exchangeRateService.convertToCurrency(fromCurrency, toCurrency, amount);

        senderExchangeAccount.setBalance(senderExchangeAccount.getBalance().add(amount));
        receiverExchangeAccount.setBalance(receiverExchangeAccount.getBalance().subtract(amountToReceive));
        exchangeAccountRepository.save(senderExchangeAccount);
        exchangeAccountRepository.save(receiverExchangeAccount);

        return amountToReceive;
    }
}
