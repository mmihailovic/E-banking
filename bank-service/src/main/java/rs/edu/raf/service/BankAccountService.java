package rs.edu.raf.service;

import rs.edu.raf.dto.*;
import rs.edu.raf.model.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.List;
/**
 * Service interface for managing accounts.
 */
public interface BankAccountService {

    /**
     * Creates a new foreign currency account based on the provided DTO.
     *
     * @param foreignCurrencyBankAccountCreateDTO The DTO containing information about the new foreign currency account.
     * @param creator ID of the employee who created bank account
     * @return The created foreign currency account.
     */
    BankAccountDTO createForeignCurrencyBankAccount(ForeignCurrencyBankAccountCreateDTO foreignCurrencyBankAccountCreateDTO, Long creator);

    /**
     * Creates a new legal entity account based on the provided DTO.
     *
     * @param businessBankAccountCreateDTO The DTO containing information about the new legal entity account.
     * @param creator ID of the employee who created bank account
     * @return The created legal entity account.
     */
    BankAccountDTO createBusinessBankAccount(BusinessBankAccountCreateDTO businessBankAccountCreateDTO, Long creator);

    /**
     * Creates a new current account based on the provided DTO.
     *
     * @param currentBankAccountCreateDTO The DTO containing information about the new current account.
     * @param creator ID of the employee who created bank account
     * @return The created current account.
     */
    BankAccountDTO createCurrentBankAccount(CurrentBankAccountCreateDTO currentBankAccountCreateDTO, Long creator);

    /**
     * Lists all accounts of a single user based on the user's ID.
     *
     * @param ownerId The ID of the user.
     * @return A list of account DTOs belonging to the user.
     */
    List<BankAccountDTO> getAllBankAccountsForOwner(Long ownerId);

    /**
     * Finds an active account by its ID.
     *
     * @param id The ID of the account to find.
     * @return The active account DTO if found, otherwise null.
     */
    BankAccountDTO findActiveBankAccountByID(Long id);

    /**
     * Finds an active account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The active account DTO if found, otherwise null.
     */
    BankAccountDTO findActiveBankAccountByAccountNumber(Long accountNumber);

    /**
     * Generates an account number for bank account according to the
     * ISO 7064 standard (MODUL 97).
     *
     * @param bankAccount The bank account.
     * @return The generated account number.
     */
    Long generateBankAccountNumber(BankAccount bankAccount);

    /**
     * Deactivates a bank account based on the account number.
     *
     * @param bankAccountNumber The account number to deactivate.
     * @return true if the deactivation is successful, otherwise false.
     */
    boolean deactivateBankAccount(Long bankAccountNumber);

    /**
     * Finds the user by the account number.
     *
     * @param accountNumber The account number to search for.
     * @return The ID of the user if found, otherwise null.
     */
    Long findOwnerOfBankAccountWithNumber(Long accountNumber);

    /**
     * Finds the account by the account number.
     *
     * @param accountNumber The account number to search for.
     * @return The {@link BankAccountDTO} object representing found account
     */
    BankAccountDTO findBankAccountByNumber(Long accountNumber);

    /**
     * Process the transaction between bank accounts
     * @param paymentQueueDTO DTO object contains information about payment
     */
    void processPayment(PaymentQueueDTO paymentQueueDTO);

    /**
     * Transfer money from sender to receiver
     * @param sender Sender's bank account
     * @param receiver Receiver's bank account
     * @param amount amount of money to be transferred
     * @param amountToReceive amount of money which receiver receives. This can be different from amount\
     *                        if currencies are different
     */
    void transferBetweenAccounts(BankAccount sender, BankAccount receiver, BigDecimal amount, BigDecimal amountToReceive);

    /**
     * Deducts available funds from bank account with specified account number
     * @param owner the ID of the owner of the bank account
     * @param amount amount of money
     * @return True if deducts is successful, otherwise false
     */
    boolean deductAvailableBalanceFromBankAccount(Long owner, BigDecimal amount);

    /**
     * Processes transaction related with listing order
     * @param buyerId ID of the user which buy listing
     * @param sellerId ID of the user which sell listing
     * @param buyPrice amount of money to be deducted from buyer's bank account
     * @param sellPrice amount of money to be added to seller's bank account
     */
    void processListingOrder(Long buyerId, Long sellerId, BigDecimal buyPrice, BigDecimal sellPrice);
}

