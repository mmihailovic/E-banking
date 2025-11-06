package org.example.bankservicekotlin.dto

import java.math.BigDecimal

data class CreditRequestDTO(
    val id: Long?,
    val creditType: CreditTypeDTO,
    val loanAmount: BigDecimal,
    val loanPurpose: String,
    val salary: BigDecimal,
    val phoneNumber: String,
    val permanentEmployee: Boolean,
    val currentEmploymentPeriod: Int,
    val loanTerm: Long,
    val bankAccount: BankAccountDTO,
    val creditRequestStatus: String
)
