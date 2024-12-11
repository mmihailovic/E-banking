package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.PaymentCode;

@Repository
public interface PaymentCodeRepository extends JpaRepository<PaymentCode, Long> {
}
