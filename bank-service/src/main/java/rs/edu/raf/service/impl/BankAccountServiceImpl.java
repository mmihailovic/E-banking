package rs.edu.raf.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import rs.edu.raf.dto.*;
import rs.edu.raf.exceptions.BankAccountNotFoundException;
import rs.edu.raf.mapper.BankAccountMapper;
import rs.edu.raf.model.accounts.*;
import rs.edu.raf.model.accounts.BankAccount;
import rs.edu.raf.model.accounts.CurrentBankAccount;
import rs.edu.raf.repository.accounts.BankAccountRepository;
import rs.edu.raf.repository.accounts.CurrentBankAccountRepository;
import rs.edu.raf.security.JwtUtil;
import rs.edu.raf.service.ExchangeAccountService;
import rs.edu.raf.service.ExchangeInvoiceService;
import rs.edu.raf.service.BankAccountService;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    @Value("${transactions.exchange}")
    private String TRANSACTION_EXCHANGE;
    @Value("${transactions.success.routing.key}")
    private String TRANSACTION_SUCCESS_ROUTING_KEY;
    @Value("${transactions.failed.routing.key}")
    private String TRANSACTION_FAILED_ROUTING_KEY;
    @Value("${bank.code}")
    private String BANK_CODE;
    private final BankAccountMapper bankAccountMapper;
    private final BankAccountRepository bankAccountRepository;
    private final CurrentBankAccountRepository currentBankAccountRepository;
    private final ExchangeInvoiceService exchangeInvoiceService;
    private final ExchangeAccountService exchangeAccountService;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate userServiceRestTemplate;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public BankAccountDTO createForeignCurrencyBankAccount(ForeignCurrencyBankAccountCreateDTO foreignCurrencyBankAccountCreateDTO, Long creator) {
        ForeignCurrencyBankAccount foreignCurrencyBankAccount = bankAccountRepository.save(
                bankAccountMapper.foreignCurrencyBankAccountCreateDtoToForeignCurrencyBankAccount(foreignCurrencyBankAccountCreateDTO, creator));
        foreignCurrencyBankAccount.setAccountNumber(generateBankAccountNumber(foreignCurrencyBankAccount));

        Long response = addAccountToClient(foreignCurrencyBankAccount, foreignCurrencyBankAccountCreateDTO.JMBG());

        if(response != null) {
            foreignCurrencyBankAccount.setOwner(response);
            return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.save(foreignCurrencyBankAccount));
        }

        throw new RuntimeException();
    }

    @Override
    @Transactional
    public BankAccountDTO createBusinessBankAccount(BusinessBankAccountCreateDTO businessBankAccountCreateDTO, Long creator) {
        BusinessBankAccount businessBankAccount = bankAccountRepository.save(
                bankAccountMapper.businessBankAccountCreateDtoToBusinessBankAccount(creator));
        businessBankAccount.setAccountNumber(generateBankAccountNumber(businessBankAccount));

        Long response = addAccountToCompany(businessBankAccount, businessBankAccountCreateDTO.TIN());

        if(response != null) {
            businessBankAccount.setOwner(response);
            return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.save(businessBankAccount));
        }

        throw new RuntimeException();
    }

    @Override
    @Transactional
    public BankAccountDTO createCurrentBankAccount(CurrentBankAccountCreateDTO currentBankAccountCreateDTO, Long creator) {
        CurrentBankAccount currentCurrencyBankAccount = bankAccountRepository.save(
                bankAccountMapper.currentBankAccountCreateDTOtoCurrentBankAccount(currentBankAccountCreateDTO, creator));
        currentCurrencyBankAccount.setAccountNumber(generateBankAccountNumber(currentCurrencyBankAccount));

        Long response = addAccountToClient(currentCurrencyBankAccount, currentBankAccountCreateDTO.JBMG());

        if(response != null) {
            currentCurrencyBankAccount.setOwner(response);
            return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.save(currentCurrencyBankAccount));
        }

        bankAccountRepository.delete(currentCurrencyBankAccount);
        throw new RuntimeException();
    }

    @Override
    public List<BankAccountDTO> getAllBankAccountsForOwner(Long ownerId) {
        return bankAccountRepository.findAllByOwner(ownerId)
                .stream()
                .map(bankAccountMapper::bankAccountToBankAccountDTO)
                .toList();
    }

    @Override
    public BankAccountDTO findActiveBankAccountByID(Long id) {
        return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with ID " + id + " not found!")));
    }

    @Override
    public BankAccountDTO findActiveBankAccountByAccountNumber(Long accountNumber) {
        return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository
                .findByAccountNumberAndActiveIsTrue(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with number " + accountNumber + " not found!")));
    }


    @Override
    public Long generateBankAccountNumber(BankAccount bankAccount) {
        long bankAccountNumber = Long.parseLong(BANK_CODE + String.format("%013d", bankAccount.getId()) + "00");
        return bankAccountNumber + 98 - bankAccountNumber % 97;
    }

    @Override
    public boolean deactivateBankAccount(Long bankAccountNumber) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumberAndActiveIsTrue(bankAccountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with number + " + bankAccountNumber + " not found!"));

        bankAccount.setActive(false);
        bankAccountRepository.save(bankAccount);
        return true;
    }

    @Override
    public Long findOwnerOfBankAccountWithNumber(Long accountNumber) {
        return findBankAccountByNumber(accountNumber).owner();
    }

    public BankAccountDTO findBankAccountByNumber(Long accountNumber){
        return bankAccountMapper.bankAccountToBankAccountDTO(bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new BankAccountNotFoundException("Bank account with number " + accountNumber + " not found!")));
    }

    @Override
    public void processPayment(PaymentQueueDTO paymentQueueDTO) {
        Optional<BankAccount> optionalSender = bankAccountRepository.findByAccountNumber(paymentQueueDTO.senderAccountNumber());
        Optional<BankAccount> optionalReceiver = bankAccountRepository.findByAccountNumber(paymentQueueDTO.receiverAccountNumber());

        Long paymentId = paymentQueueDTO.id();

        if(optionalSender.isEmpty() || optionalReceiver.isEmpty()) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString());
            return;
        }

        BankAccount sender = optionalSender.get();
        BankAccount receiver = optionalReceiver.get();

        BigDecimal amount = paymentQueueDTO.amount();

        if(!sender.getActive() || !receiver.getActive() || sender.getAvailableBalance().compareTo(amount) < 0
            || sender.getOwner().longValue() != paymentQueueDTO.userId().longValue()) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString());
            return;
        }

        if(paymentQueueDTO.type().equals("INTERNAL") && sender.getOwner().longValue() != receiver.getOwner().longValue()) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString());
            return;
        }

        if(sender.getCurrency().getId().longValue() == receiver.getCurrency().getId().longValue()) {
            transferBetweenAccounts(sender, receiver, amount, amount);
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_SUCCESS_ROUTING_KEY, paymentId.toString());
            return;
        }

        try {
            BigDecimal amountToReceive = exchangeAccountService.exchange(sender.getCurrency().getCode(),
                    receiver.getCurrency().getCode(), amount);

            transferBetweenAccounts(sender, receiver, amount, amountToReceive);
            exchangeInvoiceService.createInvoiceForExchange(sender, receiver, amount);
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_SUCCESS_ROUTING_KEY, paymentId.toString());
        } catch (Exception e) {
            rabbitTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_FAILED_ROUTING_KEY, paymentId.toString());
        }
    }

    @Override
    public void transferBetweenAccounts(BankAccount sender, BankAccount receiver, BigDecimal amount, BigDecimal amountToReceive) {
        sender.setBalance(sender.getBalance().subtract(amount));
        sender.setAvailableBalance(sender.getAvailableBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amountToReceive));
        receiver.setAvailableBalance(receiver.getAvailableBalance().add(amountToReceive));
        bankAccountRepository.save(sender);
        bankAccountRepository.save(receiver);
    }

    @Override
    public boolean deductAvailableBalanceFromBankAccount(Long owner, BigDecimal amount) {
        Optional<BankAccount> optionalBankAccount = bankAccountRepository.findByOwnerAndCurrency_Code(owner, "RSD");

        if(optionalBankAccount.isEmpty()) {
            return false;
        }

        BankAccount bankAccount = optionalBankAccount.get();

        if(bankAccount.getAvailableBalance().compareTo(amount) < 0) {
            return false;
        }

        bankAccount.setAvailableBalance(bankAccount.getAvailableBalance().subtract(amount));
        bankAccountRepository.save(bankAccount);
        return true;
    }

    @Override
    public void processListingOrder(Long buyerId, Long sellerId, BigDecimal buyPrice, BigDecimal sellPrice) {
        BankAccount buyerBankAccount = bankAccountRepository.findByOwnerAndCurrency_Code(buyerId, "RSD")
                .orElseThrow(()-> new BankAccountNotFoundException("Bank account for user with ID " + buyerId + " not found!"));

        BankAccount sellerBankAccount = bankAccountRepository.findByOwnerAndCurrency_Code(sellerId, "RSD")
                .orElseThrow(()-> new BankAccountNotFoundException("Bank account for user with ID " + buyerId + " not found!"));

        buyerBankAccount.setBalance(buyerBankAccount.getBalance().subtract(buyPrice));
        if(buyerBankAccount.getBalance().compareTo(buyerBankAccount.getAvailableBalance()) < 0) {
            buyerBankAccount.setAvailableBalance(buyerBankAccount.getBalance());
        }

        sellerBankAccount.setBalance(sellerBankAccount.getBalance().add(sellPrice));
        sellerBankAccount.setAvailableBalance(sellerBankAccount.getAvailableBalance().add(sellPrice));

        bankAccountRepository.save(buyerBankAccount);
        bankAccountRepository.save(sellerBankAccount);
    }

    private Long addAccountToClient(BankAccount bankAccount, String JMBG) {
        Long bankAccountNumber = bankAccount.getAccountNumber();

        return userServiceRestTemplate.exchange("/clients/"+JMBG + "/account/" + bankAccountNumber,
                HttpMethod.PUT, new HttpEntity<>(jwtUtil.extractToken()), Long.class).getBody();
    }

    private Long addAccountToCompany(BankAccount bankAccount, Integer TIN) {
        Long bankAccountNumber = bankAccount.getAccountNumber();

        return userServiceRestTemplate.exchange("/company/"+TIN + "/account/" + bankAccountNumber,
                HttpMethod.PUT, new HttpEntity<>(jwtUtil.extractToken()), Long.class).getBody();
    }
}
