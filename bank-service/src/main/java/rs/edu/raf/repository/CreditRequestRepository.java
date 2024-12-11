package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.edu.raf.model.credit.CreditRequest;
import rs.edu.raf.model.credit.CreditRequestStatus;

import java.util.List;

public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {
    List<CreditRequest> findAllByCreditRequestStatus(CreditRequestStatus creditRequestStatus);
    List<CreditRequest> findAllByBankAccount_OwnerAndCreditRequestStatus(Long owner, CreditRequestStatus creditRequestStatus);
}
