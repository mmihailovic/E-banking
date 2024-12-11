package rs.edu.raf.repository.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.accounts.BusinessBankAccount;

@Repository
public interface BusinessBankAccountRepository extends JpaRepository<BusinessBankAccount,Long> {

}
