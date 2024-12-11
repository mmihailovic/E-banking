package rs.edu.raf.repository.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.accounts.BankAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    Optional<BankAccount> findByAccountNumber(Long accountNumber);
    List<BankAccount> findAllByOwner(Long owner);
    Optional<BankAccount> findByIdAndActiveIsTrue(Long id);
    Optional<BankAccount> findByAccountNumberAndActiveIsTrue(Long accountNumber);
    Optional<BankAccount> findByOwnerAndCurrency_Code(Long owner, String currencyCode);
}
