package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.ExchangeAccount;

import java.util.Optional;

@Repository
public interface ExchangeAccountRepository extends JpaRepository<ExchangeAccount, Long> {
    Optional<ExchangeAccount> findByCurrencyCode(String currency);
}
