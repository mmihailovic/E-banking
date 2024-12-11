package rs.edu.raf.service;

import rs.edu.raf.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * This interface defines methods for managing exchange rates.
 */
public interface ExchangeRateService {

    /**
     * Retrieves all exchange rates.
     *
     * @return A list of ExchangeRateResponseDto objects representing all exchange rates.
     */
    List<ExchangeRateDto> getAllExchangeRates();

    /**
     * Converts an amount from one currency to another.
     *
     * @param oldCurrencyCode The currency code of the amount to convert.
     * @param newCurrencyCode The currency code to which the amount will be converted.
     * @param amount The amount to convert.
     * @return The converted amount as a BigDecimal.
     */
    BigDecimal convertToCurrency(String oldCurrencyCode, String newCurrencyCode, BigDecimal amount);

    /**
     * This method calculates exchange rate between currencies
     * @param oldCurrencyCode the currency code of the source currency (e.g., "USD", "EUR").
     * @param newCurrencyCode the currency code of the target currency (e.g., "GBP", "JPY").
     * @return the exchange rate as a {@link BigDecimal}, representing how many units of
     *           the target currency are equivalent to one unit of the source currency.
     */
    BigDecimal exchangeRate(String oldCurrencyCode, String newCurrencyCode);


}
