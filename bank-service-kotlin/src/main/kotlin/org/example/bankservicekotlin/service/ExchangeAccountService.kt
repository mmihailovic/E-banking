package org.example.bankservicekotlin.service

import java.math.BigDecimal

interface ExchangeAccountService {
    /**
     * This method exchange specified amount of money from one specified currency to other specified currency
     * @param fromCurrency the currency code of source currency
     * @param toCurrency the currency code of target currency
     * @param amount amount of money to be exchanged
     * @return [BigDecimal] object representing amount of money in target currency
     */
    @Throws(Exception::class)
    fun exchange(fromCurrency: String, toCurrency: String, amount: BigDecimal): BigDecimal
}