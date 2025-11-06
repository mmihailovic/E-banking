package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.dto.ExchangeInvoiceDTO
import org.example.bankservicekotlin.mapper.ExchangeInvoiceMapper
import org.example.bankservicekotlin.model.ExchangeInvoice
import org.example.bankservicekotlin.model.accounts.BankAccount
import org.example.bankservicekotlin.repository.ExchangeInvoiceRepository
import org.example.bankservicekotlin.service.ExchangeInvoiceService
import org.example.bankservicekotlin.service.ExchangeRateService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ExchangeInvoiceServiceImpl(
    private val exchangeInvoiceRepository: ExchangeInvoiceRepository,
    private val exchangeInvoiceMapper: ExchangeInvoiceMapper,
    private val exchangeRateService: ExchangeRateService
) : ExchangeInvoiceService {
    override fun createInvoiceForExchange(sender: BankAccount, receiver: BankAccount, amount: BigDecimal) {
        val exchangeRate = exchangeRateService.exchangeRate(
            sender.currency.code,
            receiver.currency.code
        )
        val exchangeInvoice = ExchangeInvoice(
            null,
            sender,
            receiver,
            amount,
            sender.currency,
            receiver.currency,
            exchangeRate,
            amount.multiply(BigDecimal("0.005")),
            System.currentTimeMillis()
        )
        exchangeInvoiceRepository.save(exchangeInvoice)
    }

    override fun listInvoicesByCurrency(currency: String): List<ExchangeInvoiceDTO> {
        return exchangeInvoiceRepository.findAllBySenderCurrency_Code(currency)
            .stream()
            .map(exchangeInvoiceMapper::exchangeInvoiceToExchangeInvoiceDTO)
            .toList()
    }
}