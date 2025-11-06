package org.example.bankservicekotlin.repository.accounts

import org.example.bankservicekotlin.model.accounts.BankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BankAccountRepository : JpaRepository<BankAccount, Long> {
    fun findByAccountNumber(accountNumber: Long): Optional<BankAccount>
    fun findAllByOwner(owner: Long): List<BankAccount>
    fun findByIdAndActiveIsTrue(id: Long): Optional<BankAccount>
    fun findByAccountNumberAndActiveIsTrue(accountNumber: Long): Optional<BankAccount>
    fun findByOwnerAndCurrency_Code(owner: Long, currencyCode: String): Optional<BankAccount>
    fun findAllByOwnerAndCurrency_IdAndActiveIsTrue(owner: Long, currencyId: Long): List<BankAccount>
}