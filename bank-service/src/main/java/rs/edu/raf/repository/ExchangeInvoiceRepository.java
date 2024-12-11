package rs.edu.raf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.edu.raf.model.ExchangeInvoice;

import java.util.List;

@Repository
public interface ExchangeInvoiceRepository extends JpaRepository<ExchangeInvoice,Long> {
    List<ExchangeInvoice> findAllBySenderCurrency_Code(String curr);
    List<ExchangeInvoice> findAllByReceiverCurrency_Code(String curr);
}
