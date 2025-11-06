package org.example.bankservicekotlin.service

import org.example.bankservicekotlin.dto.CountryCreateDTO
import org.example.bankservicekotlin.dto.CountryDTO

interface CountryService {
    /**
     * Creates country
     * @param countryCreateDTO DTO which contains information about country
     * @return [CountryDTO] object representing created country
     */
    fun createCountry(countryCreateDTO: CountryCreateDTO): CountryDTO

    /**
     * Gets all countries
     * @return List of [CountryDTO] representing countries
     */
    fun getAllCountries(): List<CountryDTO>

    /**
     * Deletes country
     * @param id the id of the country to be deleted
     */
    fun deleteCountry(id: Long)
}