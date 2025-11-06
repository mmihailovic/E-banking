package org.example.bankservicekotlin.service

import org.example.bankservicekotlin.dto.CurrencyCreateDTO
import org.example.bankservicekotlin.dto.CurrencyDTO

interface CurrencyService {
    /**
     * Creates currency
     * @param currencyCreateDTO DTO which contains information about currency
     * @return [CurrencyDTO] object representing created currency
     */
    fun createCurrency(currencyCreateDTO: CurrencyCreateDTO): CurrencyDTO

    /**
     * Gets all currencies
     * @return List of [CurrencyDTO] representing currencies
     */
    fun getAllCurrencies(): List<CurrencyDTO>

    /**
     * Deletes currency
     * @param id the id of the currency to be deleted
     */
    fun deleteCurrency(id: Long)
}