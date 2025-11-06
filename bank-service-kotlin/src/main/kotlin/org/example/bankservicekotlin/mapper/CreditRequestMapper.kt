package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.dto.CreditRequestCreateDTO
import org.example.bankservicekotlin.dto.CreditRequestDTO
import org.example.bankservicekotlin.model.credit.CreditRequest
import org.example.bankservicekotlin.model.credit.CreditRequestStatus
import org.example.bankservicekotlin.model.credit.CreditType
import org.example.bankservicekotlin.repository.CurrencyRepository
import org.example.bankservicekotlin.repository.accounts.BankAccountRepository
import org.springframework.stereotype.Component

@Component
class CreditRequestMapper(
    private val bankAccountRepository: BankAccountRepository,
    private val currencyRepository: CurrencyRepository,
    private val currencyMapper: CurrencyMapper,
    private val bankAccountMapper: BankAccountMapper,
    private val creditTypeMapper: CreditTypeMapper
) {

    fun creditRequestCreateDtoToCreditRequest(
        creditRequestCreateDto: CreditRequestCreateDTO,
        creditType: CreditType
    ): CreditRequest {
        val bankAccount =
            bankAccountRepository.findByAccountNumber(creditRequestCreateDto.bankAccountNumber).orElseThrow()
        return CreditRequest(
            null,
            null,
            creditType,
            creditRequestCreateDto.amount,
            creditRequestCreateDto.loanPurpose,
            creditRequestCreateDto.salary,
            creditRequestCreateDto.phoneNumber,
            creditRequestCreateDto.permanentEmployee,
            creditRequestCreateDto.currentEmploymentPeriod,
            creditRequestCreateDto.loanTerm,
            bankAccount,
            CreditRequestStatus.PENDING,
            null
        )
    }

    fun creditRequestToCreditRequestDto(creditRequest: CreditRequest): CreditRequestDTO {
        return CreditRequestDTO(
            creditRequest.id, creditTypeMapper.creditTypeToCreditTypeDTO(creditRequest.creditType),
            creditRequest.loanAmount, creditRequest.loanPurpose, creditRequest.salary,
            creditRequest.phoneNumber, creditRequest.permanentEmployee,
            creditRequest.currentEmploymentPeriod, creditRequest.loanTerm,
            bankAccountMapper.bankAccountToBankAccountDTO(creditRequest.bankAccount),
            creditRequest.creditRequestStatus.toString()
        )
    }
}