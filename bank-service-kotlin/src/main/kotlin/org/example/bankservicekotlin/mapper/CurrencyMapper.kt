package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.dto.CurrencyDTO
import org.example.bankservicekotlin.dto.CurrencyCreateDTO
import org.example.bankservicekotlin.model.Currency
import org.example.bankservicekotlin.repository.CountryRepository
import org.springframework.stereotype.Component

@Component
class CurrencyMapper(private val countryMapper: CountryMapper, private val countryRepository: CountryRepository) {
    fun currencyCreateDTOtoCurrency(currencyCreateDTO: CurrencyCreateDTO): Currency {
        val country = countryRepository.findById(currencyCreateDTO.countryId).orElseThrow()
        return Currency(null, currencyCreateDTO.name, currencyCreateDTO.code, currencyCreateDTO.symbol, country)
    }

    fun currencyToCurrencyDTO(currency: Currency): CurrencyDTO {
        return CurrencyDTO(
            currency.id,
            currency.name,
            currency.code,
            currency.symbol,
            countryMapper.countryToCountryDTO(currency.country)
        )
    }
}