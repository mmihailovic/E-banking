package rs.edu.raf.repository.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.accounts.ForeignCurrencyBankAccount;

@Repository
public interface ForeignCurrencyBankAccountRepository extends JpaRepository<ForeignCurrencyBankAccount,Long> {

}
