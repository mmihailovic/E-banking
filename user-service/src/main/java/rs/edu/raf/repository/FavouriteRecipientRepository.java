package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.FavouriteRecipient;

import java.util.List;

@Repository
public interface FavouriteRecipientRepository extends JpaRepository<FavouriteRecipient, Long> {
    List<FavouriteRecipient> findByClient_Id(Long clientId);
}
