package rs.edu.raf.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.edu.raf.dto.ForeignCurrencyBankAccountCreateDTO;
import rs.edu.raf.dto.BusinessBankAccountCreateDTO;
import rs.edu.raf.dto.CurrentBankAccountCreateDTO;
import rs.edu.raf.dto.BankAccountDTO;
import rs.edu.raf.model.accounts.*;
import rs.edu.raf.repository.CurrencyRepository;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class BankAccountMapper {
    private CurrencyMapper currencyMapper;
    private CurrencyRepository currencyRepository;

    public BankAccountDTO bankAccountToBankAccountDTO(BankAccount bankAccount) {
        return new BankAccountDTO(bankAccount.getId(), bankAccount.getAccountNumber().toString(), bankAccount.getOwner(),
                bankAccount.getBalance(), bankAccount.getAvailableBalance(), bankAccount.getCreator(),
                bankAccount.getCreationDate(), currencyMapper.currencyToCurrencyDTO(bankAccount.getCurrency()),
                bankAccount.getActive());
    }

    public ForeignCurrencyBankAccount foreignCurrencyBankAccountCreateDtoToForeignCurrencyBankAccount
            (ForeignCurrencyBankAccountCreateDTO foreignCurrencyBankAccountCreateDTO, Long creator) {
        ForeignCurrencyBankAccount foreignCurrencyBankAccount = new ForeignCurrencyBankAccount();

        foreignCurrencyBankAccount.setMaintenancePrice(foreignCurrencyBankAccountCreateDTO.maintenancePrice());
        foreignCurrencyBankAccount.setOwner(foreignCurrencyBankAccountCreateDTO.owner());
        foreignCurrencyBankAccount.setBalance(BigDecimal.ZERO);
        foreignCurrencyBankAccount.setAvailableBalance(BigDecimal.ZERO);
        foreignCurrencyBankAccount.setCreator(creator);
        foreignCurrencyBankAccount.setCreationDate(System.currentTimeMillis());
        foreignCurrencyBankAccount.setCurrency(currencyRepository.findById(foreignCurrencyBankAccountCreateDTO.currencyId()).orElseThrow());
        foreignCurrencyBankAccount.setActive(true);

        return foreignCurrencyBankAccount;
    }

    public BusinessBankAccount businessBankAccountCreateDtoToBusinessBankAccount
            (BusinessBankAccountCreateDTO businessBankAccountCreateDTO, Long creator) {
        BusinessBankAccount businessBankAccount = new BusinessBankAccount();

        businessBankAccount.setOwner(businessBankAccountCreateDTO.companyId());
        businessBankAccount.setBalance(BigDecimal.ZERO);
        businessBankAccount.setAvailableBalance(BigDecimal.ZERO);
        businessBankAccount.setCreator(creator);
        businessBankAccount.setCreationDate(System.currentTimeMillis());
        businessBankAccount.setCurrency(currencyRepository.findByCode("RSD").orElseThrow());
        businessBankAccount.setActive(true);

        return businessBankAccount;
    }

    public CurrentBankAccount currentBankAccountCreateDTOtoCurrentBankAccount
            (CurrentBankAccountCreateDTO currentBankAccountCreateDTO, Long creator) {
        CurrentBankAccount currentBankAccount = new CurrentBankAccount();

        currentBankAccount.setOwner(currentBankAccountCreateDTO.owner());
        currentBankAccount.setBalance(BigDecimal.ZERO);
        currentBankAccount.setAvailableBalance(BigDecimal.ZERO);
        currentBankAccount.setCreator(creator);
        currentBankAccount.setCreationDate(System.currentTimeMillis());
        currentBankAccount.setCurrency(currencyRepository.findByCode("RSD").orElseThrow());
        currentBankAccount.setActive(true);
        currentBankAccount.setMaintenancePrice(currentBankAccountCreateDTO.maintenancePrice());
        currentBankAccount.setType(CurrentBankAccountType.valueOf(currentBankAccountCreateDTO.accountType()));

        return currentBankAccount;
    }
}
