package org.example.bankservicekotlin.service.impl

import jakarta.transaction.Transactional
import org.example.bankservicekotlin.dto.*
import org.example.bankservicekotlin.exception.BankAccountNotFoundException
import org.example.bankservicekotlin.mapper.BankAccountMapper
import org.example.bankservicekotlin.model.accounts.BankAccount
import org.example.bankservicekotlin.model.accounts.BusinessBankAccount
import org.example.bankservicekotlin.model.accounts.CurrentBankAccount
import org.example.bankservicekotlin.model.accounts.ForeignCurrencyBankAccount
import org.example.bankservicekotlin.repository.accounts.BankAccountRepository
import org.example.bankservicekotlin.repository.accounts.CurrentBankAccountRepository
import org.example.bankservicekotlin.security.JwtUtil
import org.example.bankservicekotlin.service.BankAccountService
import org.example.bankservicekotlin.service.ExchangeAccountService
import org.example.bankservicekotlin.service.ExchangeInvoiceService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.util.*

@Service
class BankAccountServiceImpl(
    @Value("\${transactions.exchange}")
    val TRANSACTION_EXCHANGE: String,
    @Value("\${transactions.success.routing.key}")
    val TRANSACTION_SUCCESS_ROUTING_KEY: String,
    @Value("\${transactions.failed.routing.key}")
    val TRANSACTION_FAILED_ROUTING_KEY: String,
    @Value("\${bank.code}")
    val BANK_CODE: String,
    private val bankAccountMapper: BankAccountMapper,
    private val bankAccountRepository: BankAccountRepository,
    private val currentBankAccountRepository: CurrentBankAccountRepository,
    private val exchangeInvoiceService: ExchangeInvoiceService,
    private val exchangeAccountService: ExchangeAccountService,
    private val rabbitTemplate: RabbitTemplate,
    private val userServiceRestTemplate: RestTemplate,
    private val jwtUtil: JwtUtil,
) : BankAccountService {
    @Transactional
    override fun createForeignCurrencyBankAccount(
        foreignCurrencyBankAccountCreateDTO: ForeignCurrencyBankAccountCreateDTO,
        creator: Long
    ): BankAccountDTO {
        val foreignCurrencyBankAccount: ForeignCurrencyBankAccount = bankAccountRepository.save(
            bankAccountMapper.foreignCurrencyBankAccountCreateDtoToForeignCurrencyBankAccount(
                foreignCurrencyBankAccountCreateDTO,
                creator
            )
        )
        foreignCurrencyBankAccount.accountNumber = generateBankAccountNumber(foreignCurrencyBankAccount)

        val response = addAccountToClient(foreignCurrencyBankAccount, foreignCurrencyBankAccountCreateDTO.JMBG)

        if (response != null) {
            foreignCurrencyBankAccount.owner = response
            return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.save(foreignCurrencyBankAccount))
        }

        throw RuntimeException()
    }

    @Transactional
    override fun createBusinessBankAccount(
        businessBankAccountCreateDTO: BusinessBankAccountCreateDTO,
        creator: Long
    ): BankAccountDTO {
        val businessBankAccount: BusinessBankAccount = bankAccountRepository.save(
            bankAccountMapper.businessBankAccountCreateDtoToBusinessBankAccount(creator)
        )
        businessBankAccount.accountNumber = generateBankAccountNumber(businessBankAccount)

        val response = addAccountToCompany(businessBankAccount, businessBankAccountCreateDTO.TIN)

        if (response != null) {
            businessBankAccount.owner = response
            return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.save(businessBankAccount))
        }

        throw RuntimeException()
    }

    @Transactional
    override fun createCurrentBankAccount(
        currentBankAccountCreateDTO: CurrentBankAccountCreateDTO,
        creator: Long
    ): BankAccountDTO {
        val currentCurrencyBankAccount: CurrentBankAccount = bankAccountRepository.save(
            bankAccountMapper.currentBankAccountCreateDTOtoCurrentBankAccount(currentBankAccountCreateDTO, creator)
        )
        currentCurrencyBankAccount.accountNumber = generateBankAccountNumber(currentCurrencyBankAccount)

        val response = addAccountToClient(currentCurrencyBankAccount, currentBankAccountCreateDTO.JMBG)

        if (response != null) {
            currentCurrencyBankAccount.owner = response
            return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.save(currentCurrencyBankAccount))
        }

        bankAccountRepository.delete(currentCurrencyBankAccount)
        throw RuntimeException()
    }

    override fun getAllBankAccountsForOwner(ownerId: Long): List<BankAccountDTO> {
        return bankAccountRepository.findAllByOwner(ownerId)
            .stream()
            .map(bankAccountMapper::bankAccountToBankAccountDTO)
            .toList()
    }

    override fun getAllBankAccountsWithSpecifiedCurrencyForOwner(
        ownerId: Long,
        currencyId: Long
    ): List<BankAccountDTO> {
        return bankAccountRepository.findAllByOwnerAndCurrency_IdAndActiveIsTrue(ownerId, currencyId)
            .stream()
            .map(bankAccountMapper::bankAccountToBankAccountDTO)
            .toList()
    }

    override fun getAllBankAccounts(): List<BankAccountDTO> {
        return bankAccountRepository.findAll()
            .stream()
            .map(bankAccountMapper::bankAccountToBankAccountDTO)
            .toList()
    }

    override fun findActiveBankAccountByID(id: Long): BankAccountDTO {
        return bankAccountMapper.bankAccountToBankAccountDTO(
            bankAccountRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow { BankAccountNotFoundException("Bank account with ID $id not found!") })
    }

    override fun findActiveBankAccountByAccountNumber(accountNumber: Long): BankAccountDTO {
        return bankAccountMapper.bankAccountToBankAccountDTO(
            bankAccountRepository
                .findByAccountNumberAndActiveIsTrue(accountNumber)
                .orElseThrow { BankAccountNotFoundException("Bank account with number $accountNumber not found!") })
    }


    override fun generateBankAccountNumber(bankAccount: BankAccount): Long {
        val bankAccountNumber = (BANK_CODE + java.lang.String.format("%013d", bankAccount.id) + "00").toLong()
        return bankAccountNumber + 98 - bankAccountNumber % 97
    }

    override fun deactivateBankAccount(bankAccountNumber: Long): Boolean {
        val bankAccount: BankAccount = bankAccountRepository.findByAccountNumberAndActiveIsTrue(bankAccountNumber)
            .orElseThrow { BankAccountNotFoundException("Bank account with number + $bankAccountNumber not found!") }

        bankAccount.active = false
        bankAccountRepository.save(bankAccount)
        return true
    }

    override fun findOwnerOfBankAccountWithNumber(accountNumber: Long): Long {
        return findBankAccountByNumber(accountNumber).owner!!
    }

    override fun findBankAccountByNumber(accountNumber: Long): BankAccountDTO {
        return bankAccountMapper.bankAccountToBankAccountDTO(
            bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow { BankAccountNotFoundException("Bank account with number $accountNumber not found!") })
    }

    override fun processPayment(paymentQueueDTO: PaymentQueueDTO) {
        val optionalSender: Optional<BankAccount> =
            bankAccountRepository.findByAccountNumber(paymentQueueDTO.senderAccountNumber)
        val optionalReceiver: Optional<BankAccount> =
            bankAccountRepository.findByAccountNumber(paymentQueueDTO.receiverAccountNumber)

        val paymentId: Long = paymentQueueDTO.id

        if (optionalSender.isEmpty() || optionalReceiver.isEmpty()) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString())
            return
        }

        val sender: BankAccount = optionalSender.get()
        val receiver: BankAccount = optionalReceiver.get()

        val amount: BigDecimal = paymentQueueDTO.amount

        if (!sender.active || !receiver.active || sender.availableBalance < amount || sender.owner != paymentQueueDTO.userId
        ){
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString())
            return
        }

        if (paymentQueueDTO.type == "INTERNAL" && sender.owner != receiver.owner
        ) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString())
            return
        }

        if (sender.currency.id === receiver.currency.id) {
            transferBetweenAccounts(sender, receiver, amount, amount)
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_SUCCESS_ROUTING_KEY, paymentId.toString())
            return
        }

        try {
            val amountToReceive = exchangeAccountService.exchange(
                sender.currency.code,
                receiver.currency.code, amount
            )

            transferBetweenAccounts(sender, receiver, amount, amountToReceive)
            exchangeInvoiceService.createInvoiceForExchange(sender, receiver, amount)
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_SUCCESS_ROUTING_KEY, paymentId.toString())
        } catch (e: Exception) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString())
        }
    }

    override fun transferBetweenAccounts(
        sender: BankAccount,
        receiver: BankAccount,
        amount: BigDecimal,
        amountToReceive: BigDecimal
    ) {
        sender.balance = sender.balance.subtract(amount)
        sender.availableBalance = sender.availableBalance.subtract(amount)
        receiver.balance = receiver.balance.add(amountToReceive)
        receiver.availableBalance = receiver.availableBalance.add(amountToReceive)
        bankAccountRepository.save(sender)
        bankAccountRepository.save(receiver)
    }

    override fun deductAvailableBalanceFromBankAccount(owner: Long, amount: BigDecimal): Boolean {
        val optionalBankAccount: Optional<BankAccount> =
            bankAccountRepository.findByOwnerAndCurrency_Code(owner, "RSD")

        if (optionalBankAccount.isEmpty) {
            return false
        }

        val bankAccount: BankAccount = optionalBankAccount.get()

        if (bankAccount.availableBalance < amount) {
            return false
        }

        bankAccount.availableBalance = bankAccount.availableBalance.subtract(amount)
        bankAccountRepository.save(bankAccount)
        return true
    }

    override fun processListingOrder(buyerId: Long, sellerId: Long, buyPrice: BigDecimal, sellPrice: BigDecimal) {
        val buyerBankAccount: BankAccount = bankAccountRepository.findByOwnerAndCurrency_Code(buyerId, "RSD")
            .orElseThrow { BankAccountNotFoundException("Bank account for user with ID $buyerId not found!") }

        val sellerBankAccount: BankAccount = bankAccountRepository.findByOwnerAndCurrency_Code(sellerId, "RSD")
            .orElseThrow { BankAccountNotFoundException("Bank account for user with ID $buyerId not found!") }

        buyerBankAccount.balance = buyerBankAccount.balance.subtract(buyPrice)
        if (buyerBankAccount.balance < buyerBankAccount.availableBalance) {
            buyerBankAccount.availableBalance = buyerBankAccount.balance
        }

        sellerBankAccount.balance = sellerBankAccount.balance.add(sellPrice)
        sellerBankAccount.availableBalance = sellerBankAccount.availableBalance.add(sellPrice)

        bankAccountRepository.save(buyerBankAccount)
        bankAccountRepository.save(sellerBankAccount)
    }

    private fun addAccountToClient(bankAccount: BankAccount, JMBG: String): Long? {
        val bankAccountNumber: Long = bankAccount.accountNumber!!

        return userServiceRestTemplate.exchange(
            "/kotlin/clients/$JMBG/account/$bankAccountNumber",
            HttpMethod.PUT, generateHeader(), Long::class.java
        ).body
    }

    private fun addAccountToCompany(bankAccount: BankAccount, TIN: Int): Long? {
        val bankAccountNumber: Long = bankAccount.accountNumber!!

        return userServiceRestTemplate.exchange(
            "/kotlin/company/$TIN/account/$bankAccountNumber",
            HttpMethod.PUT, generateHeader(), Long::class.java
        ).body
    }

    private fun generateHeader(): HttpEntity<String> {
        val authHeader = HttpHeaders()
        authHeader["Cookie"] = "AuthToken=" + jwtUtil.extractToken()
        return HttpEntity(authHeader)
    }
}