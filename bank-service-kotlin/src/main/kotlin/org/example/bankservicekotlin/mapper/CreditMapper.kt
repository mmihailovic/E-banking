package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.model.credit.Credit
import org.example.bankservicekotlin.model.credit.CreditRequest
import org.example.bankservicekotlin.dto.CreditDTO
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class CreditMapper(private val creditRequestMapper: CreditRequestMapper) {
    fun creditRequestToCredit(creditRequest: CreditRequest): Credit {
        val loanMaturityDate = LocalDateTime
            .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault())
            .plusMonths(creditRequest.loanTerm)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val nextInstallmentDate = LocalDateTime
            .ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault())
            .plusMonths(1)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        return Credit(
            null,
            creditRequest,
            System.currentTimeMillis(),
            loanMaturityDate,
            creditRequest.loanAmount,
            creditRequest.loanAmount,
            nextInstallmentDate
        )
    }

    fun creditToCreditDTO(credit: Credit): CreditDTO {
        return CreditDTO(
            creditRequestMapper.creditRequestToCreditRequestDto(credit.creditRequest),
            credit.contractDate, credit.loanMaturityDate, credit.installmentAmount,
            credit.remainingDebt, credit.nextInstallmentDate
        )
    }
}