package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.dto.CountryDTO
import org.example.bankservicekotlin.dto.CountryCreateDTO
import org.example.bankservicekotlin.model.Country
import org.springframework.stereotype.Component

@Component
class CountryMapper {
    fun countryCreateDTOtoCountry(countryCreateDTO: CountryCreateDTO): Country {
        return Country(null, countryCreateDTO.name)
    }

    fun countryToCountryDTO(country: Country): CountryDTO {
        return CountryDTO(country.id, country.name)
    }
}