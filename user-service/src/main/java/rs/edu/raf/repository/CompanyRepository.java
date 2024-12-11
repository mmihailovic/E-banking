package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.user.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
