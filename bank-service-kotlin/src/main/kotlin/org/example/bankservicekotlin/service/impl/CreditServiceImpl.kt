package org.example.bankservicekotlin.service.impl

import jakarta.transaction.Transactional
import org.example.bankservicekotlin.dto.CreditDTO
import org.example.bankservicekotlin.dto.CreditRequestCreateDTO
import org.example.bankservicekotlin.dto.CreditRequestDTO
import org.example.bankservicekotlin.exception.*
import org.example.bankservicekotlin.mapper.CreditMapper
import org.example.bankservicekotlin.mapper.CreditRequestMapper
import org.example.bankservicekotlin.model.accounts.BankAccount
import org.example.bankservicekotlin.model.credit.Credit
import org.example.bankservicekotlin.model.credit.CreditRequest
import org.example.bankservicekotlin.model.credit.CreditRequestStatus
import org.example.bankservicekotlin.model.credit.CreditType
import org.example.bankservicekotlin.repository.CreditRepository
import org.example.bankservicekotlin.repository.CreditRequestRepository
import org.example.bankservicekotlin.repository.CreditTypeRepository
import org.example.bankservicekotlin.repository.accounts.BankAccountRepository
import org.example.bankservicekotlin.service.CreditService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.ZoneId
import java.util.stream.Collectors

@Service
class CreditServiceImpl(
    private val creditRequestRepository: CreditRequestRepository,
    private val creditRequestMapper: CreditRequestMapper,
    private val creditRepository: CreditRepository,
    private val creditTypeRepository: CreditTypeRepository,
    private val creditMapper: CreditMapper,
    private val bankAccountRepository: BankAccountRepository
): CreditService {
    override fun applyForCredit(creditRequestCreateDto: CreditRequestCreateDTO): CreditRequestDTO {
        val creditType: CreditType = creditTypeRepository.findById(creditRequestCreateDto.creditTypeId)
            .orElseThrow { CreditTypeNotFoundException(creditRequestCreateDto.creditTypeId) }

        val loanTerm: Long = creditRequestCreateDto.loanTerm
        val loanAmount: BigDecimal = creditRequestCreateDto.amount

        if (loanTerm < creditType.minLoanTerm || loanTerm > creditType.maxLoanTerm) throw WrongCreditLoanTermException(
            creditType.minLoanTerm,
            creditType.maxLoanTerm
        )

        if (loanAmount > creditType.maxLoanAmount) throw WrongLoanAmountException(creditType.maxLoanAmount)

        val creditRequest: CreditRequest =
            creditRequestMapper.creditRequestCreateDtoToCreditRequest(creditRequestCreateDto, creditType)
        return creditRequestMapper.creditRequestToCreditRequestDto(creditRequestRepository.save(creditRequest))
    }

    @Transactional
    override fun approveCreditRequest(id: Long): CreditRequestDTO {
        val creditRequest: CreditRequest = creditRequestRepository.findById(id)
            .orElseThrow { CreditRequestNotFoundException(id) }

        creditRequest.creditRequestStatus = CreditRequestStatus.APPROVED
        creditRequestRepository.save(creditRequest)
        createCredit(creditRequest)

        val bankAccount: BankAccount = creditRequest.bankAccount
        bankAccount.balance = bankAccount.balance.add(creditRequest.loanAmount)
        bankAccount.availableBalance = bankAccount.availableBalance.add(creditRequest.loanAmount)
        bankAccountRepository.save(bankAccount)

        return creditRequestMapper.creditRequestToCreditRequestDto(creditRequest)
    }

    override fun dennyCreditRequest(id: Long): CreditRequestDTO {
        val creditRequest: CreditRequest = creditRequestRepository.findById(id)
            .orElseThrow { CreditRequestNotFoundException(id) }

        creditRequest.creditRequestStatus = CreditRequestStatus.DENIED
        creditRequestRepository.save(creditRequest)
        return creditRequestMapper.creditRequestToCreditRequestDto(creditRequest)
    }

    override fun getAllCreditRequestsWithStatus(status: String): List<CreditRequestDTO> {
        val creditRequestStatus: CreditRequestStatus = CreditRequestStatus.valueOf(status)
        return creditRequestRepository.findAllByCreditRequestStatus(creditRequestStatus)
            .stream()
            .map(creditRequestMapper::creditRequestToCreditRequestDto)
            .toList()
    }

    override fun getAllCreditRequestForUserWithStatus(userId: Long, status: String): List<CreditRequestDTO> {
        val creditRequestStatus: CreditRequestStatus = CreditRequestStatus.valueOf(status)
        return creditRequestRepository.findAllByBankAccount_OwnerAndCreditRequestStatus(userId, creditRequestStatus)
            .stream()
            .map(creditRequestMapper::creditRequestToCreditRequestDto)
            .collect(Collectors.toList())
    }


    override fun createCredit(creditRequest: CreditRequest) {
        val credit: Credit = creditMapper.creditRequestToCredit(creditRequest)
        credit.installmentAmount = calculateInstallmentAmountForCredit(creditRequest)
        creditRepository.save(credit)
    }

    override fun getAllCredits(): List<CreditDTO> {
        return creditRepository.findAll()
            .stream()
            .map(creditMapper::creditToCreditDTO)
            .toList()
    }

    override fun processCreditInstallment() {
        val credits: List<Credit> = creditRepository.findAllByRemainingDebtGreaterThan(BigDecimal.ZERO)

        val now = System.currentTimeMillis()

        val nextInstallmentDate = LocalDate.now().plusMonths(1).atStartOfDay()
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        for (credit in credits) {
            if (credit.nextInstallmentDate <= now) {
                val bankAccount: BankAccount = credit.creditRequest.bankAccount
                bankAccount.balance = bankAccount.balance.subtract(credit.installmentAmount)
                bankAccount.availableBalance = bankAccount.availableBalance.subtract(credit.installmentAmount)
                credit.remainingDebt = credit.remainingDebt.subtract(credit.installmentAmount)
                credit.nextInstallmentDate = nextInstallmentDate
            }
        }
    }

    override fun getDetailedCredit(id: Long): CreditDTO {
        return creditMapper.creditToCreditDTO(
            creditRepository.findById(id)
                .orElseThrow { CreditNotFoundException(id) })
    }

    override fun calculateInstallmentAmountForCredit(creditRequest: CreditRequest): BigDecimal {
        val loanTerm :Int = creditRequest.loanTerm.toInt()
        val loanAmount = creditRequest.loanAmount

        val monthlyRate: BigDecimal = creditRequest.creditType.nominalInterestRate
            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
            .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP)

        val numerator = monthlyRate.multiply(BigDecimal.ONE.add(monthlyRate).pow(loanTerm))
        val denominator = BigDecimal.ONE.add(monthlyRate).pow(loanTerm).subtract(BigDecimal.ONE)

        val monthlyPayment = loanAmount.multiply(numerator).divide(denominator, RoundingMode.HALF_UP)

        return monthlyPayment.setScale(2, RoundingMode.HALF_UP)
    }
}