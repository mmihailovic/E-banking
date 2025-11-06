package org.example.bankservicekotlin.repository.accounts

import org.example.bankservicekotlin.model.accounts.ForeignCurrencyBankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ForeignCurrencyBankAccountRepository:JpaRepository<ForeignCurrencyBankAccount, Long> {
}