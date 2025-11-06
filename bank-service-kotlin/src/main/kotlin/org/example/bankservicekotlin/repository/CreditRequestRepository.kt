package org.example.bankservicekotlin.repository

import org.example.bankservicekotlin.model.credit.CreditRequest
import org.example.bankservicekotlin.model.credit.CreditRequestStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CreditRequestRepository : JpaRepository<CreditRequest, Long> {
    fun findAllByCreditRequestStatus(creditRequestStatus: CreditRequestStatus): List<CreditRequest>
    fun findAllByBankAccount_OwnerAndCreditRequestStatus(
        owner: Long,
        creditRequestStatus: CreditRequestStatus
    ): List<CreditRequest>
}