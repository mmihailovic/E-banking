package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.exception.ExchangeAccountNotFoundException
import org.example.bankservicekotlin.model.ExchangeAccount
import org.example.bankservicekotlin.repository.ExchangeAccountRepository
import org.example.bankservicekotlin.service.ExchangeAccountService
import org.example.bankservicekotlin.service.ExchangeRateService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ExchangeAccountServiceImpl(
    private val exchangeAccountRepository: ExchangeAccountRepository,
    private val exchangeRateService: ExchangeRateService): ExchangeAccountService {

    @Throws(Exception::class)
    override fun exchange(fromCurrency: String, toCurrency: String, amount: BigDecimal): BigDecimal {
        val senderExchangeAccount: ExchangeAccount = exchangeAccountRepository.findByCurrencyCode(fromCurrency)
            .orElseThrow { ExchangeAccountNotFoundException(fromCurrency) }

        val receiverExchangeAccount: ExchangeAccount = exchangeAccountRepository.findByCurrencyCode(toCurrency)
            .orElseThrow { ExchangeAccountNotFoundException(toCurrency) }

        val amountToReceive = exchangeRateService.convertToCurrency(fromCurrency, toCurrency, amount)

        senderExchangeAccount.balance = senderExchangeAccount.balance.add(amount)
        receiverExchangeAccount.balance = receiverExchangeAccount.balance.subtract(amountToReceive)
        exchangeAccountRepository.save(senderExchangeAccount)
        exchangeAccountRepository.save(receiverExchangeAccount)

        return amountToReceive
    }

}