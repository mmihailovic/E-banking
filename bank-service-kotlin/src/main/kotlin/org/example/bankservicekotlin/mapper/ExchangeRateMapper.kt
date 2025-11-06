package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.dto.ExchangeRateDTO
import org.example.bankservicekotlin.model.ExchangeRate
import org.springframework.stereotype.Component

@Component
class ExchangeRateMapper {
    fun exchangeRateToExchangeRateResponseDto(exchangeRate: ExchangeRate): ExchangeRateDTO {
        return ExchangeRateDTO(exchangeRate.currencyCode, exchangeRate.rate)
    }
}