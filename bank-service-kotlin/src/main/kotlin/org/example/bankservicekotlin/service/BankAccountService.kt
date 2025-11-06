package org.example.bankservicekotlin.service

import org.example.bankservicekotlin.dto.*
import org.example.bankservicekotlin.model.accounts.BankAccount
import java.math.BigDecimal

interface BankAccountService {
    /**
     * Creates a new foreign currency account based on the provided DTO.
     *
     * @param foreignCurrencyBankAccountCreateDTO The DTO containing information about the new foreign currency account.
     * @param creator ID of the employee who created bank account
     * @return The created foreign currency account.
     */
    fun createForeignCurrencyBankAccount(
        foreignCurrencyBankAccountCreateDTO: ForeignCurrencyBankAccountCreateDTO,
        creator: Long
    ): BankAccountDTO

    /**
     * Creates a new legal entity account based on the provided DTO.
     *
     * @param businessBankAccountCreateDTO The DTO containing information about the new legal entity account.
     * @param creator ID of the employee who created bank account
     * @return The created legal entity account.
     */
    fun createBusinessBankAccount(
        businessBankAccountCreateDTO: BusinessBankAccountCreateDTO,
        creator: Long
    ): BankAccountDTO

    /**
     * Creates a new current account based on the provided DTO.
     *
     * @param currentBankAccountCreateDTO The DTO containing information about the new current account.
     * @param creator ID of the employee who created bank account
     * @return The created current account.
     */
    fun createCurrentBankAccount(
        currentBankAccountCreateDTO: CurrentBankAccountCreateDTO,
        creator: Long
    ): BankAccountDTO

    /**
     * Lists all accounts of a single user based on the user's ID.
     *
     * @param ownerId The ID of the user.
     * @return A list of account DTOs belonging to the user.
     */
    fun getAllBankAccountsForOwner(ownerId: Long): List<BankAccountDTO>

    /**
     * Lists accounts with specified currency of a single user based on the user's ID.
     *
     * @param ownerId The ID of the user.
     * @param currencyId The ID of the currency
     * @return A list of [BankAccountDTO] objects representing the accounts with
     * the specified currency belonging to the user.
     */
    fun getAllBankAccountsWithSpecifiedCurrencyForOwner(ownerId: Long, currencyId: Long): List<BankAccountDTO>

    /**
     * Retrieves all bank accounts
     * @return List of [BankAccountDTO] objects representing bank accountss
     */
    fun getAllBankAccounts(): List<BankAccountDTO>

    /**
     * Finds an active account by its ID.
     *
     * @param id The ID of the account to find.
     * @return The active account DTO if found, otherwise null.
     */
    fun findActiveBankAccountByID(id: Long): BankAccountDTO

    /**
     * Finds an active account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The active account DTO if found, otherwise null.
     */
    fun findActiveBankAccountByAccountNumber(accountNumber: Long): BankAccountDTO

    /**
     * Generates an account number for bank account according to the
     * ISO 7064 standard (MODUL 97).
     *
     * @param bankAccount The bank account.
     * @return The generated account number.
     */
    fun generateBankAccountNumber(bankAccount: BankAccount): Long

    /**
     * Deactivates a bank account based on the account number.
     *
     * @param bankAccountNumber The account number to deactivate.
     * @return true if the deactivation is successful, otherwise false.
     */
    fun deactivateBankAccount(bankAccountNumber: Long): Boolean

    /**
     * Finds the user by the account number.
     *
     * @param accountNumber The account number to search for.
     * @return The ID of the user if found, otherwise null.
     */
    fun findOwnerOfBankAccountWithNumber(accountNumber: Long): Long

    /**
     * Finds the account by the account number.
     *
     * @param accountNumber The account number to search for.
     * @return The [BankAccountDTO] object representing found account
     */
    fun findBankAccountByNumber(accountNumber: Long): BankAccountDTO

    /**
     * Process the transaction between bank accounts
     * @param paymentQueueDTO DTO object contains information about payment
     */
    fun processPayment(paymentQueueDTO: PaymentQueueDTO)

    /**
     * Transfer money from sender to receiver
     * @param sender Sender's bank account
     * @param receiver Receiver's bank account
     * @param amount amount of money to be transferred
     * @param amountToReceive amount of money which receiver receives. This can be different from amount\
     * if currencies are different
     */
    fun transferBetweenAccounts(
        sender: BankAccount,
        receiver: BankAccount,
        amount: BigDecimal,
        amountToReceive: BigDecimal
    )

    /**
     * Deducts available funds from bank account with specified account number
     * @param owner the ID of the owner of the bank account
     * @param amount amount of money
     * @return True if deducts is successful, otherwise false
     */
    fun deductAvailableBalanceFromBankAccount(owner: Long, amount: BigDecimal): Boolean

    /**
     * Processes transaction related with listing order
     * @param buyerId ID of the user which buy listing
     * @param sellerId ID of the user which sell listing
     * @param buyPrice amount of money to be deducted from buyer's bank account
     * @param sellPrice amount of money to be added to seller's bank account
     */
    fun processListingOrder(buyerId: Long, sellerId: Long, buyPrice: BigDecimal, sellPrice: BigDecimal)
}