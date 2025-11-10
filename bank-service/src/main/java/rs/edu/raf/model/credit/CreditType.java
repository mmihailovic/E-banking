package rs.edu.raf.model.credit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rs.edu.raf.model.Currency;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class CreditType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal nominalInterestRate;
    private int minLoanTerm;
    private int maxLoanTerm;
    private BigDecimal maxLoanAmount;
    private BigDecimal prepayment;
    @ManyToOne
    private Currency currency;
}
