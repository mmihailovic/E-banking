package org.example.bankservicekotlin.service

import org.example.bankservicekotlin.dto.ExchangeRateDTO
import java.math.BigDecimal

interface ExchangeRateService {
    /**
     * Retrieves all exchange rates.
     *
     * @return A list of ExchangeRateResponseDto objects representing all exchange rates.
     */
    fun getAllExchangeRates(): List<ExchangeRateDTO>

    /**
     * Converts an amount from one currency to another.
     *
     * @param oldCurrencyCode The currency code of the amount to convert.
     * @param newCurrencyCode The currency code to which the amount will be converted.
     * @param amount The amount to convert.
     * @return The converted amount as a BigDecimal.
     */
    fun convertToCurrency(oldCurrencyCode: String, newCurrencyCode: String, amount: BigDecimal): BigDecimal

    /**
     * This method calculates exchange rate between currencies
     * @param oldCurrencyCode the currency code of the source currency (e.g., "USD", "EUR").
     * @param newCurrencyCode the currency code of the target currency (e.g., "GBP", "JPY").
     * @return the exchange rate as a [BigDecimal], representing how many units of
     * the target currency are equivalent to one unit of the source currency.
     */
    fun exchangeRate(oldCurrencyCode: String, newCurrencyCode: String): BigDecimal
}