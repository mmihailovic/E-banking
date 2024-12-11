package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.edu.raf.model.code.Code;
import rs.edu.raf.model.code.CodeType;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code,Long> {
    Optional<Code> findByUserEmailAndCodeType(String email, CodeType codeType);
}
