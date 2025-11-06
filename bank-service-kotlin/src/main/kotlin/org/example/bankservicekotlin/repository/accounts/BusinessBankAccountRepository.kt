package org.example.bankservicekotlin.repository.accounts

import org.example.bankservicekotlin.model.accounts.BusinessBankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BusinessBankAccountRepository: JpaRepository<BusinessBankAccount, Long> {
}