package rs.edu.raf.service;

import rs.edu.raf.dto.ExchangeInvoiceDTO;
import rs.edu.raf.model.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeInvoiceService {
    /**
     * This method creates invoice for exchange
     * @param sender {@link BankAccount} object representing source account
     * @param receiver {@link BankAccount} object representing receiver account
     * @param amount amount of money which is exchanged from source
     */
    void createInvoiceForExchange(BankAccount sender, BankAccount receiver, BigDecimal amount);

    /**
     * This method returns all invoices for currency
     * @param currency code of the currency
     * @return List of {@link ExchangeInvoiceDTO} objects representing invoices
     */
    List<ExchangeInvoiceDTO> listInvoicesByCurrency(String currency);
}
