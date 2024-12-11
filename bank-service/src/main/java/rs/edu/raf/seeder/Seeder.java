package rs.edu.raf.seeder;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.edu.raf.model.Country;
import rs.edu.raf.model.Currency;
import rs.edu.raf.model.accounts.BusinessBankAccount;
import rs.edu.raf.model.accounts.CurrentBankAccount;
import rs.edu.raf.model.accounts.CurrentBankAccountType;
import rs.edu.raf.repository.CountryRepository;
import rs.edu.raf.repository.CurrencyRepository;
import rs.edu.raf.repository.accounts.BankAccountRepository;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class Seeder implements CommandLineRunner {
    private CountryRepository countryRepository;
    private BankAccountRepository bankAccountRepository;
    private CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) throws Exception {
        Country country = insertCountry();
        Currency currency = insertCurrency(country);
        insertOurBankAccount(currency);
        insertClientBankAccount(currency);
    }

    private Country insertCountry() {
        Country country = new Country();
        country.setName("Serbia");
        return countryRepository.save(country);
    }

    private Currency insertCurrency(Country country) {
        Currency currency = new Currency();
        currency.setCode("RSD");
        currency.setName("Serbian dinar");
        currency.setSymbol("din");
        currency.setCountry(country);

        return currencyRepository.save(currency);
    }

    private void insertOurBankAccount(Currency currency) {
        BusinessBankAccount businessBankAccount = new BusinessBankAccount();
        businessBankAccount.setAccountNumber(123L);
        businessBankAccount.setActive(true);
        businessBankAccount.setBalance(new BigDecimal("100000.0"));
        businessBankAccount.setCurrency(currency);
        businessBankAccount.setOwner(1L);
        businessBankAccount.setCreationDate(System.currentTimeMillis());
        businessBankAccount.setAvailableBalance(new BigDecimal("100000.0"));
        businessBankAccount.setCreator(1l);

        bankAccountRepository.save(businessBankAccount);
    }

    private void insertClientBankAccount(Currency currency) {
        CurrentBankAccount currentBankAccount = new CurrentBankAccount();
        currentBankAccount.setAccountNumber(456L);
        currentBankAccount.setActive(true);
        currentBankAccount.setBalance(new BigDecimal("100000.0"));
        currentBankAccount.setCurrency(currency);
        currentBankAccount.setOwner(3L);
        currentBankAccount.setCreationDate(System.currentTimeMillis());
        currentBankAccount.setAvailableBalance(new BigDecimal("100000.0"));
        currentBankAccount.setCreator(1l);
        currentBankAccount.setType(CurrentBankAccountType.PERSONAL);
        currentBankAccount.setMaintenancePrice(new BigDecimal("0.0"));

        bankAccountRepository.save(currentBankAccount);
    }
}
