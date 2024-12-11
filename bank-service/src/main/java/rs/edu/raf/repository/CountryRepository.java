package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
