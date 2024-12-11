package rs.edu.raf.repository.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.accounts.CurrentBankAccount;

import java.util.Optional;

@Repository
public interface CurrentBankAccountRepository extends JpaRepository<CurrentBankAccount,Long> {
    Optional<CurrentBankAccount> findByOwner(Long owner);
}
