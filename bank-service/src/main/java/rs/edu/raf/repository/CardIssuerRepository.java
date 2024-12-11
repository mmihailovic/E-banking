package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.card.CardIssuer;

@Repository
public interface CardIssuerRepository extends JpaRepository<CardIssuer, Long> {
}
