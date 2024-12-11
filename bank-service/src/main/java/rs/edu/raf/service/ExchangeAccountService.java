package rs.edu.raf.service;

import java.math.BigDecimal;

public interface ExchangeAccountService {
    /**
     * This method exchange specified amount of money from one specified currency to other specified currency
     * @param fromCurrency the currency code of source currency
     * @param toCurrency the currency code of target currency
     * @param amount amount of money to be exchanged
     * @return {@link BigDecimal} object representing amount of money in target currency
     */
    BigDecimal exchange(String fromCurrency, String toCurrency, BigDecimal amount) throws Exception;
}
