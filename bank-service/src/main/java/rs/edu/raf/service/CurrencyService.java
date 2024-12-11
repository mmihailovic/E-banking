package rs.edu.raf.service;

import rs.edu.raf.dto.CurrencyCreateDTO;
import rs.edu.raf.dto.CurrencyDTO;

import java.util.List;

public interface CurrencyService {
    /**
     * Creates currency
     * @param currencyCreateDTO DTO which contains information about currency
     * @return {@link CurrencyDTO} object representing created currency
     */
    CurrencyDTO createCurrency(CurrencyCreateDTO currencyCreateDTO);

    /**
     * Gets all currencies
     * @return List of {@link CurrencyDTO} representing currencies
     */
    List<CurrencyDTO> getAllCurrencies();

    /**
     * Deletes currency
     * @param id the id of the currency to be deleted
     */
    void deleteCurrency(Long id);
}
