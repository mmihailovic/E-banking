package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.dto.CreditTypeDTO
import org.example.bankservicekotlin.dto.CreditTypeCreateDTO
import org.example.bankservicekotlin.model.credit.CreditType
import org.example.bankservicekotlin.repository.CurrencyRepository
import org.springframework.stereotype.Component

@Component
class CreditTypeMapper(
    private val currencyRepository: CurrencyRepository, private val currencyMapper: CurrencyMapper
) {
    fun creditTypeCreateDTOtoCreditType(creditTypeCreateDTO: CreditTypeCreateDTO): CreditType {
        val currency = currencyRepository.findById(creditTypeCreateDTO.currencyId).orElseThrow()
        return CreditType(
            null,
            creditTypeCreateDTO.name,
            creditTypeCreateDTO.nominalInterestRate,
            creditTypeCreateDTO.minLoanTerm,
            creditTypeCreateDTO.maxLoanTerm,
            creditTypeCreateDTO.maxLoanAmount,
            creditTypeCreateDTO.prepayment,
            currency
        )
    }

    fun creditTypeToCreditTypeDTO(creditType: CreditType): CreditTypeDTO {
        return CreditTypeDTO(
            creditType.id,
            creditType.name,
            creditType.nominalInterestRate,
            creditType.minLoanTerm,
            creditType.maxLoanTerm,
            creditType.maxLoanAmount,
            creditType.prepayment,
            currencyMapper.currencyToCurrencyDTO(creditType.currency)
        )
    }
}