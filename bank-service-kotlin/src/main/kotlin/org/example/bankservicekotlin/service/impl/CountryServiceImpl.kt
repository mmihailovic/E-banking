package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.dto.CountryCreateDTO
import org.example.bankservicekotlin.dto.CountryDTO
import org.example.bankservicekotlin.exception.CountryNotFoundException
import org.example.bankservicekotlin.mapper.CountryMapper
import org.example.bankservicekotlin.model.Country
import org.example.bankservicekotlin.repository.CountryRepository
import org.example.bankservicekotlin.service.CountryService
import org.springframework.stereotype.Service

@Service
class CountryServiceImpl(
    private val countryRepository: CountryRepository,
    private val countryMapper: CountryMapper): CountryService {
    override fun createCountry(countryCreateDTO: CountryCreateDTO): CountryDTO {
        return countryMapper.countryToCountryDTO(
            countryRepository.save(
                countryMapper.countryCreateDTOtoCountry(
                    countryCreateDTO
                )
            )
        )
    }

    override fun getAllCountries(): List<CountryDTO> {
        return countryRepository.findAll().stream().map(countryMapper::countryToCountryDTO).toList()
    }

    override fun deleteCountry(id: Long) {
        val country: Country = countryRepository.findById(id)
            .orElseThrow { CountryNotFoundException(id) }

        countryRepository.delete(country)
    }
}