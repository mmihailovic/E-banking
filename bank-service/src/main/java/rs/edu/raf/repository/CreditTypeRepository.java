package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.credit.CreditType;

@Repository
public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
}
