package rs.edu.raf.model.accounts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.edu.raf.model.Currency;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long accountNumber;
    private Long owner;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private Long creator; // employee who created account
    private Long creationDate;
    @ManyToOne
    private Currency currency;
    private Boolean active;
}
