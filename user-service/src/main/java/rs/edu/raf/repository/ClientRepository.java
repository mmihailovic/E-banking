package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.user.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmailAndActiveIsTrue(String email);

    Optional<Client> findByPhoneNumberAndActiveIsTrue(String phoneNumber);

    Optional<Client> findByJMBGAndActiveIsTrue(String jmbg);

    List<Client> findAllByActiveIsTrue();

    Optional<Client> findByIdAndActiveIsTrue(Long id);
}
