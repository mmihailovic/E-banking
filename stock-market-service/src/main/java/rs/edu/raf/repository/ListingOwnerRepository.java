package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.ListingOwner;

import java.util.Optional;

@Repository
public interface ListingOwnerRepository extends JpaRepository<ListingOwner, Long> {
    Optional<ListingOwner> findByOwnerAndTicker(Long owner, String ticker);
}
