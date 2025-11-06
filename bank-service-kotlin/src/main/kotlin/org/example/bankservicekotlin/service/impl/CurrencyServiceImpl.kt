package org.example.bankservicekotlin.service.impl

import org.example.bankservicekotlin.dto.CurrencyCreateDTO
import org.example.bankservicekotlin.dto.CurrencyDTO
import org.example.bankservicekotlin.exception.CurrencyNotFoundException
import org.example.bankservicekotlin.mapper.CurrencyMapper
import org.example.bankservicekotlin.model.Currency
import org.example.bankservicekotlin.repository.CurrencyRepository
import org.example.bankservicekotlin.service.CurrencyService
import org.springframework.stereotype.Service

@Service
class CurrencyServiceImpl(
    private val currencyRepository: CurrencyRepository,
    private val currencyMapper: CurrencyMapper
): CurrencyService {
    override fun createCurrency(currencyCreateDTO: CurrencyCreateDTO): CurrencyDTO {
        return currencyMapper.currencyToCurrencyDTO(
            currencyRepository.save(
                currencyMapper.currencyCreateDTOtoCurrency(
                    currencyCreateDTO
                )
            )
        )
    }

    override fun getAllCurrencies(): List<CurrencyDTO> {
        return currencyRepository.findAll().stream().map(currencyMapper::currencyToCurrencyDTO).toList()
    }

    override fun deleteCurrency(id: Long) {
        val currency: Currency = currencyRepository.findById(id)
            .orElseThrow { CurrencyNotFoundException(id) }

        currencyRepository.delete(currency)
    }
}