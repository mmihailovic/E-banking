package org.example.bankservicekotlin.mapper

import org.example.bankservicekotlin.dto.BankAccountDTO
import org.example.bankservicekotlin.dto.ForeignCurrencyBankAccountCreateDTO
import org.example.bankservicekotlin.dto.CurrentBankAccountCreateDTO
import org.example.bankservicekotlin.model.accounts.*
import org.example.bankservicekotlin.repository.CurrencyRepository
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class BankAccountMapper(private val currencyMapper: CurrencyMapper, private val currencyRepository: CurrencyRepository) {
    fun bankAccountToBankAccountDTO(bankAccount: BankAccount): BankAccountDTO {
        return BankAccountDTO(
            bankAccount.id, bankAccount.accountNumber.toString(), bankAccount.owner,
            bankAccount.balance, bankAccount.availableBalance, bankAccount.creator,
            bankAccount.creationDate, currencyMapper.currencyToCurrencyDTO(bankAccount.currency),
            bankAccount.active
        )
    }

    fun foreignCurrencyBankAccountCreateDtoToForeignCurrencyBankAccount(
        foreignCurrencyBankAccountCreateDTO: ForeignCurrencyBankAccountCreateDTO,
        creator: Long
    ): ForeignCurrencyBankAccount {
        val currency = currencyRepository.findById(foreignCurrencyBankAccountCreateDTO.currencyId).orElseThrow()
        return ForeignCurrencyBankAccount(
            null,
            null,
            null,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            creator,
            System.currentTimeMillis(),
            currency,
            true,
            foreignCurrencyBankAccountCreateDTO.maintenancePrice
        )
    }

    fun businessBankAccountCreateDtoToBusinessBankAccount(creator: Long): BusinessBankAccount {
        val currency = currencyRepository.findByCode("RSD").orElseThrow()
        return BusinessBankAccount(
            null,
            null,
            null,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            creator,
            System.currentTimeMillis(),
            currency,
            true
        )
    }

    fun currentBankAccountCreateDTOtoCurrentBankAccount(
        currentBankAccountCreateDTO: CurrentBankAccountCreateDTO,
        creator: Long
    ): CurrentBankAccount {
        val currency = currencyRepository.findByCode("RSD").orElseThrow();
        return CurrentBankAccount(
            null,
            null,
            null,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            creator,
            System.currentTimeMillis(),
            currency,
            true,
            CurrentBankAccountType.valueOf(currentBankAccountCreateDTO.accountType),
            currentBankAccountCreateDTO.maintenancePrice
        )
    }
}