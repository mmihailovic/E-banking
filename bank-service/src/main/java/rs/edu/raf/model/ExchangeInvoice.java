package rs.edu.raf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.edu.raf.model.accounts.BankAccount;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeInvoice {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;
    @ManyToOne
    private BankAccount senderAccount;
    @ManyToOne
    private BankAccount receiverAccount;
    private BigDecimal senderAmount;
    @ManyToOne
    private Currency senderCurrency;
    @ManyToOne
    private Currency receiverCurrency;
    private BigDecimal exchangeRate;
    private BigDecimal profit;
    private Long dateAndTime;
}

