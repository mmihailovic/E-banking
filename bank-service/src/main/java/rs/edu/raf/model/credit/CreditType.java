package rs.edu.raf.model.credit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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
}
