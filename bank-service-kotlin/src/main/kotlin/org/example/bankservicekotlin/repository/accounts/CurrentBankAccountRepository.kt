package org.example.bankservicekotlin.repository.accounts

import org.example.bankservicekotlin.model.accounts.CurrentBankAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CurrentBankAccountRepository: JpaRepository<CurrentBankAccount, Long> {
    fun findByOwner(owner: Long): Optional<CurrentBankAccount>
}