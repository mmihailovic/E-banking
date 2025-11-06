package org.example.bankservicekotlin.controller

import jakarta.validation.Valid
import org.example.bankservicekotlin.dto.BankAccountDTO
import org.example.bankservicekotlin.dto.BusinessBankAccountCreateDTO
import org.example.bankservicekotlin.dto.CurrentBankAccountCreateDTO
import org.example.bankservicekotlin.dto.ForeignCurrencyBankAccountCreateDTO
import org.example.bankservicekotlin.security.JwtUtil
import org.example.bankservicekotlin.service.BankAccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/kotlin/bank-accounts")

class BankAccountController(
    private val bankAccountService: BankAccountService,
    private val jwtUtil: JwtUtil) {

    @PostMapping("/foreign-currency")
    fun createForeignCurrencyBankAccount(
        @RequestBody foreignCurrencyBankAccountCreateDTO: @Valid ForeignCurrencyBankAccountCreateDTO
    ): ResponseEntity<BankAccountDTO> {
        return ResponseEntity<BankAccountDTO>(
            bankAccountService.createForeignCurrencyBankAccount(
                foreignCurrencyBankAccountCreateDTO,
                jwtUtil.getRealIDForLoggedUser()
            ), HttpStatus.OK
        )
    }

    @PostMapping("/business")
    fun createBusinessBankAccount(
        @RequestBody businessBankAccountCreateDTO: @Valid BusinessBankAccountCreateDTO
    ): ResponseEntity<BankAccountDTO> {
        return ResponseEntity<BankAccountDTO>(
            bankAccountService.createBusinessBankAccount(
                businessBankAccountCreateDTO,
                jwtUtil.getRealIDForLoggedUser()
            ), HttpStatus.OK
        )
    }

    @PostMapping("/current")
    fun createCurrentBankAccount(
        @RequestBody currentBankAccountCreateDTO: @Valid CurrentBankAccountCreateDTO
    ): ResponseEntity<BankAccountDTO> {
        return ResponseEntity<BankAccountDTO>(
            bankAccountService.createCurrentBankAccount(
                currentBankAccountCreateDTO,
                jwtUtil.getRealIDForLoggedUser()
            ), HttpStatus.OK
        )
    }

    @GetMapping("/client/{id}")
    fun getAllBankAccountsForClient(@PathVariable("id") id: Long): ResponseEntity<List<BankAccountDTO>> {
        return ResponseEntity<List<BankAccountDTO>>(bankAccountService.getAllBankAccountsForOwner(id), HttpStatus.OK)
    }

    @GetMapping("/client/currency/{currency}")
    fun getBankAccountsWithCurrencyForLoggedClient(
        @PathVariable("currency") id: Long
    ): ResponseEntity<List<BankAccountDTO>> {
        return ResponseEntity<List<BankAccountDTO>>(
            bankAccountService.getAllBankAccountsWithSpecifiedCurrencyForOwner(
                jwtUtil.getIDForLoggedUser(),
                id
            ), HttpStatus.OK
        )
    }

    @GetMapping("/client")
    fun getAllBankAccountsForLoggedClient(): ResponseEntity<List<BankAccountDTO>> {
        return ResponseEntity<List<BankAccountDTO>>(
            bankAccountService.getAllBankAccountsForOwner(jwtUtil.getIDForLoggedUser()),
            HttpStatus.OK
        )
    }

    @GetMapping
    fun getAllBankAccounts(): ResponseEntity<List<BankAccountDTO>> {
        return ResponseEntity<List<BankAccountDTO>>(bankAccountService.getAllBankAccounts(), HttpStatus.OK)
    }

    @GetMapping("/account/{id}")
    fun getBankAccountWithID(@PathVariable("id") id: Long): ResponseEntity<BankAccountDTO> {
        return ResponseEntity<BankAccountDTO>(bankAccountService.findActiveBankAccountByID(id), HttpStatus.OK)
    }

    @GetMapping("/account/number/{accountNumber}")
    fun getBankAccountWithAccountNumber(@PathVariable("accountNumber") bankAccountNumber: Long): ResponseEntity<BankAccountDTO> {
        return ResponseEntity<BankAccountDTO>(
            bankAccountService.findActiveBankAccountByAccountNumber(bankAccountNumber),
            HttpStatus.OK
        )
    }

    @PutMapping("/deactivate/{accountNumber}")
    fun deactivateBankAccount(@PathVariable("accountNumber") accountNumber: Long): ResponseEntity<Boolean> {
        return ResponseEntity<Boolean>(bankAccountService.deactivateBankAccount(accountNumber), HttpStatus.OK)
    }

    @PutMapping("/deduct-available-balance/{owner}/{amount}")
    fun deductAvailableBalance(
        @PathVariable("amount") amount: BigDecimal,
        @PathVariable("owner") accountNumber: Long
    ): ResponseEntity<Boolean> {
        return ResponseEntity<Boolean>(
            bankAccountService.deductAvailableBalanceFromBankAccount(
                accountNumber,
                amount
            ), HttpStatus.OK
        )
    }
}