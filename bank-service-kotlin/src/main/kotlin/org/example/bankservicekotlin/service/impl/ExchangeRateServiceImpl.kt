package org.example.bankservicekotlin.service.impl

import jakarta.annotation.PostConstruct
import org.example.bankservicekotlin.dto.ExchangeRateDTO
import org.example.bankservicekotlin.exception.ExchangeRateNotFoundException
import org.example.bankservicekotlin.mapper.ExchangeRateMapper
import org.example.bankservicekotlin.model.ExchangeRate
import org.example.bankservicekotlin.repository.ExchangeRateRepository
import org.example.bankservicekotlin.service.ExchangeRateService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@Service
class ExchangeRateServiceImpl(
    @Value("\${exchange.api.url}")
    val EXCHANGE_API_URL: String,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val exchangeRateMapper: ExchangeRateMapper
): ExchangeRateService {
    @PostConstruct
    fun saveExchangeRates() {
        val restTemplate = RestTemplate()
        val exchangeRatesResponse: Map<String, Any>? = restTemplate.getForObject(EXCHANGE_API_URL, Map::class)

        if (exchangeRatesResponse != null && exchangeRatesResponse.containsKey("conversion_rates")) {
            val conversionRates = exchangeRatesResponse["conversion_rates"] as Map<String, Any>
            saveRates(conversionRates)
        }
    }

    private fun saveRates(conversionRates: Map<String, Any>) {
        exchangeRateRepository.deleteAll()
        conversionRates.forEach { (currencyCode: String, rate: Any) ->
            val exchangeRate = ExchangeRate(null, currencyCode, BigDecimal(rate.toString()))
            exchangeRateRepository.save(exchangeRate)
        }
    }

    override fun getAllExchangeRates(): List<ExchangeRateDTO> {
        return exchangeRateRepository.findAll()
            .stream()
            .map(exchangeRateMapper::exchangeRateToExchangeRateResponseDto)
            .toList()
    }

    override fun convertToCurrency(oldCurrencyCode: String, newCurrencyCode: String, amount: BigDecimal): BigDecimal {
        val oldExchangeRate: ExchangeRate = exchangeRateRepository.findByCurrencyCode(oldCurrencyCode)
            .orElseThrow { ExchangeRateNotFoundException(oldCurrencyCode) }

        val newExchangeRate: ExchangeRate = exchangeRateRepository.findByCurrencyCode(newCurrencyCode)
            .orElseThrow { ExchangeRateNotFoundException(newCurrencyCode) }

        val oldAmount: BigDecimal = oldExchangeRate.rate
        val newAmount: BigDecimal = newExchangeRate.rate

        val provision = BigDecimal.valueOf(0.995)

        return amount.divide(oldAmount, MathContext(2, RoundingMode.HALF_DOWN)).multiply(newAmount).multiply(provision)
    }

    override fun exchangeRate(oldCurrencyCode: String, newCurrencyCode: String): BigDecimal {
        val oldExchangeRate: ExchangeRate = exchangeRateRepository.findByCurrencyCode(oldCurrencyCode)
            .orElseThrow { ExchangeRateNotFoundException(oldCurrencyCode) }

        val newExchangeRate: ExchangeRate = exchangeRateRepository.findByCurrencyCode(newCurrencyCode)
            .orElseThrow { ExchangeRateNotFoundException(newCurrencyCode) }

        val oldAmount: BigDecimal = oldExchangeRate.rate
        val newAmount: BigDecimal = newExchangeRate.rate

        return BigDecimal("1.0").divide(oldAmount, MathContext(2, RoundingMode.HALF_DOWN)).multiply(newAmount)
    }
}